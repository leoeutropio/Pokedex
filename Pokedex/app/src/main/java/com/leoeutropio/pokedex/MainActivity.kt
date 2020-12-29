package com.leoeutropio.pokedex

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokedex.adapters.PokemonAdapter
import com.leoeutropio.pokedex.model.PokemonDetails
import com.leoeutropio.pokedex.model.ResultPokeApi
import com.leoeutropio.pokedex.retrofit.NetworkUtils
import com.leoeutropio.pokedex.retrofit.RequestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var retrofitClient: Retrofit
    private lateinit var endpoint: RequestInterface
    private lateinit var progress: ProgressBar
    private var limit = "20"
    private var offset = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configRetrofit()

        val pokemonRecycler = findViewById<RecyclerView>(R.id.pokemonRecycler)
        progress = findViewById(R.id.progress_circular)

        pokemonAdapter = PokemonAdapter(this)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = pokemonAdapter

        getPokemons()
    }

    private fun configRetrofit() {
        retrofitClient = NetworkUtils.getRetrofitInstance()
        endpoint = retrofitClient.create(RequestInterface::class.java)
    }

    private fun getPokemons() {
        progress.visibility = View.VISIBLE

        val callback = endpoint.getPokemon(limit, offset)
        callback.enqueue(object : Callback<ResultPokeApi> {
            override fun onResponse(call: Call<ResultPokeApi>, response: Response<ResultPokeApi>) {
                progress.visibility = View.GONE
                val result = response.body()!!.results
                pokemonAdapter.addPokemon(result)
            }

            override fun onFailure(call: Call<ResultPokeApi>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getPokemonDetails(idName: String) {
        val callback = endpoint.getPokemonDetails(idName)
        callback.enqueue(object : Callback<List<PokemonDetails>> {
            override fun onResponse(
                call: Call<List<PokemonDetails>>,
                response: Response<List<PokemonDetails>>
            ) {

            }

            override fun onFailure(call: Call<List<PokemonDetails>>, t: Throwable) {

            }

        })
    }

}