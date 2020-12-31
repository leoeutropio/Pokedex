package com.leoeutropio.pokedex.model

import com.leoeutropio.pokedex.model.abilitiesModel.Abilities
import com.leoeutropio.pokedex.model.spriteModel.Sprite
import com.leoeutropio.pokedex.model.statModel.Stats
import java.io.Serializable

data class PokemonDetails(
    val abilities:List<Abilities>,
    val id: Int,
    val name: String,
    val sprites: Sprite,
    val stats: List<Stats>,
) : Serializable