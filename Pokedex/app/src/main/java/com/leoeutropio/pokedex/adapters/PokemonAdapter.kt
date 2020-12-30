package com.leoeutropio.pokedex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokedex.R
import com.leoeutropio.pokedex.model.Pokemon
import java.util.*
import kotlin.collections.ArrayList


class PokemonAdapter(private val context: Context, var listener: PokemonClickListener) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val pokemons = ArrayList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_pokemons, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemons[position]

        val nome: String = pokemon.name.substring(0, 1).toUpperCase(Locale.getDefault()) + pokemon.name.substring(1).toLowerCase(Locale.getDefault())
        holder.nome.text = nome


        holder.cardPokemon.setOnClickListener {
            listener.selectPokemon(pokemon.name)
        }
    }

    fun clearAdapter(){
        pokemons.clear()
        notifyDataSetChanged()
    }

    fun searchAddPokemon(pokemon: Pokemon) {
        pokemons.add(pokemon)
        notifyDataSetChanged()
    }

    fun addPokemon(poke: List<Pokemon>) {
        pokemons.addAll(poke)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nomePokemon)
        val cardPokemon: CardView = itemView.findViewById(R.id.cardPokemon)
    }

    interface PokemonClickListener {
        fun selectPokemon(name: String)
    }

}