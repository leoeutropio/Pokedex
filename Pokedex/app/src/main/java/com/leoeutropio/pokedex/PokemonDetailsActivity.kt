package com.leoeutropio.pokedex

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leoeutropio.pokedex.adapters.HabilidadesAdapter
import com.leoeutropio.pokedex.model.PokemonDetails
import com.leoeutropio.pokedex.retrofit.NetworkUtils
import com.leoeutropio.pokedex.retrofit.RequestInterface
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var retrofitClient: Retrofit
    private lateinit var endpoint: RequestInterface
    private lateinit var habilidadesAdapter: HabilidadesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        backArrowImg.setOnClickListener {
            onBackPressed()
        }

        habilidadesAdapter = HabilidadesAdapter(this)
        habilidadesRv.layoutManager = LinearLayoutManager(this)
        habilidadesRv.adapter = habilidadesAdapter

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
        previewCl.visibility = View.GONE
        statsPokemonCl.visibility = View.GONE
        circularPb.visibility = View.VISIBLE

        val callback = endpoint.getPokemonDetails(name)
        callback.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>
            ) {
                previewCl.visibility = View.VISIBLE
                statsPokemonCl.visibility = View.VISIBLE
                circularPb.visibility = View.GONE
                populatePokemon(response.body()!!)

            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                previewCl.visibility = View.VISIBLE
                statsPokemonCl.visibility = View.VISIBLE
                circularPb.visibility = View.GONE
                Toast.makeText(baseContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun populatePokemon(pokemonDetails: PokemonDetails) {
        Glide.with(this)
            .load(pokemonDetails.sprites.other.officialartwork.front_default)
            .into(pokemonImg)

        val nome: String = pokemonDetails.name.substring(0, 1).toUpperCase(Locale.getDefault()) + pokemonDetails.name.substring(1).toLowerCase(Locale.getDefault())
        nomePokemonTv.text = nome

        when {
            pokemonDetails.id < 9 -> {
                idPokemonTv.text = "#00${pokemonDetails.id}"
            }
            pokemonDetails.id in 10..99 -> {
                idPokemonTv.text = "#0${pokemonDetails.id}"
            }
            pokemonDetails.id > 99 -> {
                idPokemonTv.text = "#${pokemonDetails.id}"
            }
        }

        hpNumberTv.text = pokemonDetails.stats[0].base_stat.toString()
        ataqueNumberTv.text = pokemonDetails.stats[1].base_stat.toString()
        defesaNumberTv.text = pokemonDetails.stats[2].base_stat.toString()
        velocidadeNumberTv.text = pokemonDetails.stats[5].base_stat.toString()

        habilidadesAdapter.addHabilidades(pokemonDetails.abilities)

    }

}