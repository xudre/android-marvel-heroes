package com.xudre.marvelheroes.repository

import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.model.ComicBookModel
import com.xudre.marvelheroes.service.ApiService
import com.xudre.marvelheroes.service.response.ApiResponse
import com.xudre.marvelheroes.service.response.CharacterResponse
import com.xudre.marvelheroes.service.response.ComicResponse
import com.xudre.marvelheroes.util.Paged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelRepositoryImpl(val service: ApiService) : MarvelRepository {
    override fun getCharacters(
        page: Number,
        perPage: Number,
        onSuccess: (Paged<CharacterModel>) -> Unit,
        onFail: (Throwable?) -> Unit
    ) {
        service.getCharacters("name", perPage, (page.toInt() * perPage.toInt()))
            .enqueue(object : Callback<ApiResponse<CharacterResponse>> {
                override fun onFailure(call: Call<ApiResponse<CharacterResponse>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ApiResponse<CharacterResponse>>,
                    response: Response<ApiResponse<CharacterResponse>>
                ) {
                }
            })
    }

    override fun getComicsFromCharacter(
        characterId: Number,
        page: Number,
        perPage: Number,
        onSuccess: (ComicBookModel?) -> Unit,
        onFail: (Throwable?) -> Unit
    ) {
        service.getCharacterComics(characterId,"-onsaleDate", perPage, (page.toInt() * perPage.toInt()))
            .enqueue(object : Callback<ApiResponse<ComicResponse>> {
                override fun onFailure(call: Call<ApiResponse<ComicResponse>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ApiResponse<ComicResponse>>,
                    response: Response<ApiResponse<ComicResponse>>
                ) {

                }
            })
    }

}
