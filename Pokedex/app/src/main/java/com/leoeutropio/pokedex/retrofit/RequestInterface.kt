package com.leoeutropio.pokedex.retrofit

import com.leoeutropio.pokedex.model.PokemonDetails
import com.leoeutropio.pokedex.model.ResultPokeApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RequestInterface {

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: String,
        @Query("offset") offset: String
    ): Call<ResultPokeApi>

    @GET("pokemon/{idNamePokemon}")
    fun getPokemonDetails(
        @Path("idNamePokemon") idNamePokemon: String
    ): Call<List<PokemonDetails>>

}