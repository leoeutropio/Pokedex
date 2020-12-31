package com.leoeutropio.pokedex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokedex.R
import com.leoeutropio.pokedex.model.abilitiesModel.Abilities


class HabilidadesAdapter(private val context: Context) :
    RecyclerView.Adapter<HabilidadesAdapter.ViewHolder>() {

    private val habilidades = ArrayList<Abilities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_habilidades, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habilidades.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habilidade = habilidades[position]
        holder.nomeHabilidade.text = habilidade.ability.name
    }

    fun addHabilidades(abilities: List<Abilities>) {
        habilidades.addAll(abilities)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeHabilidade: TextView = itemView.findViewById(R.id.nomeHabilidadeTv)
    }

}