package com.leoeutropio.pokedex.model

import com.leoeutropio.pokedex.model.statModel.Stats
import java.io.Serializable

data class PokemonDetails(
    val id: Int,
    val name: String,
    val sprites: Sprite,
    val stats: List<Stats>,
    val type: List<Tipos>,
) : Serializable