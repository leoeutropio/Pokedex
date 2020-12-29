package com.leoeutropio.pokedex.model.statModel

import java.io.Serializable

data class Stats(
        val base_stat: Int,
        val stat: Stat
) : Serializable