package com.leoeutropio.pokedex

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokedex.adapters.PokemonAdapter
import com.leoeutropio.pokedex.model.Pokemon
import com.leoeutropio.pokedex.model.PokemonDetails
import com.leoeutropio.pokedex.model.ResultPokeApi
import com.leoeutropio.pokedex.retrofit.NetworkUtils
import com.leoeutropio.pokedex.retrofit.RequestInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var retrofitClient: Retrofit
    private lateinit var endpoint: RequestInterface
    private var limit = "20"
    private var offset = 0
    private lateinit var result: ResultPokeApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configRetrofit()

        pokemonAdapter = PokemonAdapter(this, object : PokemonAdapter.PokemonClickListener {
            override fun selectPokemon(name: String) {
                val i = Intent(this@MainActivity, PokemonDetailsActivity::class.java)
                i.putExtra("name", name)
                startActivity(i)
            }

        })

        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = pokemonAdapter

        pokemonRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (result.next != null) {
                        offset += 20
                        progressPaginacao.visibility = View.VISIBLE
                        getPokemons()
                    }
                }
            }
        })

        searchPokemonEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progressCircular.visibility = View.VISIBLE
                nenhumTxt.visibility = View.GONE
                hideKeyboard()
                pokemonAdapter.clearAdapter()
                if (v.length() == 0) {
                    getPokemons()
                } else {
                    searchPokemon(v.text.toString())
                }
                true
            } else {
                false
            }
        }

        progressCircular.visibility = View.VISIBLE
        getPokemons()
    }


    private fun configRetrofit() {
        retrofitClient = NetworkUtils.getRetrofitInstance()
        endpoint = retrofitClient.create(RequestInterface::class.java)
    }

    private fun getPokemons() {
        val callback = endpoint.getPokemon(limit, offset.toString())
        callback.enqueue(object : Callback<ResultPokeApi> {
            override fun onResponse(call: Call<ResultPokeApi>, response: Response<ResultPokeApi>) {
                progressPaginacao.visibility = View.GONE
                progressCircular.visibility = View.GONE
                result = response.body()!!
                pokemonAdapter.addPokemon(result.results)
            }

            override fun onFailure(call: Call<ResultPokeApi>, t: Throwable) {
                progressPaginacao.visibility = View.GONE
                progressCircular.visibility = View.GONE
                Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun searchPokemon(nameId: String) {
        val callback = endpoint.getPokemonDetails(nameId)
        callback.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(call: Call<PokemonDetails>, response: Response<PokemonDetails>) {
                progressCircular.visibility = View.GONE
                if (response.body() != null) {
                    val pokemon = Pokemon(response.body()!!.name, "")
                    pokemonAdapter.searchAddPokemon(pokemon)
                } else {
                    nenhumTxt.visibility = View.VISIBLE
                }

            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                progressCircular.visibility = View.GONE
                Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.findViewById<View>(android.R.id.content).windowToken, 0);
    }
}