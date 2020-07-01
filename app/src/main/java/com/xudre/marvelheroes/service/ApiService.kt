package com.xudre.marvelheroes.service

import com.xudre.marvelheroes.service.response.ApiResponse
import com.xudre.marvelheroes.service.response.CharacterResponse
import com.xudre.marvelheroes.service.response.ComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Number,
        @Query("offset") offset: Number
    ): Call<ApiResponse<CharacterResponse>>

    @GET("/v1/public/characters/{charId}/comics")
    fun getCharacterComics(
        @Path("charId") id: Number,
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Number,
        @Query("offset") offset: Number
    ): Call<ApiResponse<ComicResponse>>
}
