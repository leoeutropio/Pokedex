package com.leoeutropio.pokedex.model

import java.io.Serializable

data class ResultPokeApi(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
) : Serializable