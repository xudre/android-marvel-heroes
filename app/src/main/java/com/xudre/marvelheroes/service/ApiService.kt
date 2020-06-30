package com.xudre.marvelheroes.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("limit") limit: Number
    )

    @GET("/v1/public/characters/{charId}/comics")
    fun getCharacterComics(
        @Path("charId") id: Number
    )
}
