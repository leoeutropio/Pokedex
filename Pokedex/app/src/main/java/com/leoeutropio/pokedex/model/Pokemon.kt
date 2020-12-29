package com.leoeutropio.pokedex.model

import java.io.Serializable

data class Pokemon(
    val name: String,
    val url: String
) : Serializable