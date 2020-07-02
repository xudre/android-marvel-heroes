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
        onSuccess: (Paged<CharacterModel>?) -> Unit,
        onFail: (Throwable?) -> Unit,
        onFinish: () -> Unit
    ) {
        service.getCharacters("name", perPage, (page.toInt() * perPage.toInt()))
            .enqueue(object : Callback<ApiResponse<CharacterResponse>> {
                override fun onFailure(call: Call<ApiResponse<CharacterResponse>>, t: Throwable) {
                    onFail(t)
                }

                override fun onResponse(
                    call: Call<ApiResponse<CharacterResponse>>,
                    response: Response<ApiResponse<CharacterResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val items = mutableListOf<CharacterModel>()
                            val onPage = it.data.offset.toLong() / it.data.count.toLong()

                            it.data.results.forEach { character ->
                                items.add(CharacterModel.factory(character))
                            }

                            onSuccess(Paged(
                                onPage,
                                it.data.count,
                                it.data.total,
                                items.toList()
                            ))

                            finished()
                        } ?: finished(Throwable("A API não retornou dados válidos."))
                    } else {
                        // TODO usar Strings
                        finished(Throwable("A API não retornou com sucesso."))
                    }
                }

                fun finished(throwable: Throwable? = null) {
                    throwable?.let(onFail)

                    onFinish()
                }
            })
    }

    override fun getComicsFromCharacter(
        characterId: Number,
        page: Number,
        perPage: Number,
        onSuccess: (Paged<ComicBookModel>?) -> Unit,
        onFail: (Throwable?) -> Unit,
        onFinish: () -> Unit
    ) {
        service.getCharacterComics(characterId,"-onsaleDate", perPage, (page.toInt() * perPage.toInt()))
            .enqueue(object : Callback<ApiResponse<ComicResponse>> {
                override fun onFailure(call: Call<ApiResponse<ComicResponse>>, t: Throwable) {
                    onFail(t)
                }

                override fun onResponse(
                    call: Call<ApiResponse<ComicResponse>>,
                    response: Response<ApiResponse<ComicResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val items = mutableListOf<ComicBookModel>()
                            val onPage = it.data.offset.toLong() / it.data.count.toLong()

                            it.data.results.forEach { comic ->
                                items.add(ComicBookModel.factory(comic))
                            }

                            onSuccess(Paged(
                                onPage,
                                it.data.count,
                                it.data.total,
                                items.toList()
                            ))

                            finished()
                        } ?: finished(Throwable("A API não retornou dados válidos."))
                    } else {
                        finished(Throwable("A API não retornou com sucesso.")) // TODO usar Strings
                    }
                }

                fun finished(throwable: Throwable? = null) {
                    throwable?.let(onFail)

                    onFinish()
                }
            })
    }

}
