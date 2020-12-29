package com.leoeutropio.pokedex

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.leoeutropio.pokedex.model.PokemonDetails
import com.leoeutropio.pokedex.retrofit.NetworkUtils
import com.leoeutropio.pokedex.retrofit.RequestInterface
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var retrofitClient: Retrofit
    private lateinit var endpoint: RequestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val intent = intent
        val name = intent.getStringExtra("name")

        configRetrofit()
        getPokemonDetails(name!!)
    }

    private fun configRetrofit() {
        retrofitClient = NetworkUtils.getRetrofitInstance()
        endpoint = retrofitClient.create(RequestInterface::class.java)
    }

    private fun getPokemonDetails(name: String) {
        progressCircular.visibility = View.VISIBLE

        val callback = endpoint.getPokemonDetails(name)
        callback.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>
            ) {
                progressCircular.visibility = View.GONE
                populatePokemon(response.body()!!)

            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                progressCircular.visibility = View.GONE
                Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun populatePokemon(pokemonDetails: PokemonDetails) {
        Glide.with(this)
            .load(pokemonDetails.sprites.other.officialartwork.front_default)
            .into(imgPokemon)
        nomePokemon.text = pokemonDetails.name
        when {
            pokemonDetails.id < 9 -> {
                idPokemon.text = "#00${pokemonDetails.id}"
            }
            pokemonDetails.id in 10..99 -> {
                idPokemon.text = "#0${pokemonDetails.id}"
            }
            pokemonDetails.id > 99 -> {
                idPokemon.text = "#${pokemonDetails.id}"
            }
        }

    }

}