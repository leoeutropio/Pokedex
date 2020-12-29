package com.leoeutropio.pokedex.model.spriteModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OtherImage (
    @SerializedName("official-artwork")
    val officialartwork: Artwork
): Serializable
