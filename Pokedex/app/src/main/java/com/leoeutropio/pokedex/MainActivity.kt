package com.leoeutropio.pokedex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokedex.adapters.PokemonAdapter
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

}