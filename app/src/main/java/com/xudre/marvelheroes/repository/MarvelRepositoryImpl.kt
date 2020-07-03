package com.xudre.marvelheroes.repository

import android.content.res.Resources
import com.xudre.marvelheroes.R
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.model.ComicBookModel
import com.xudre.marvelheroes.service.ApiService
import com.xudre.marvelheroes.service.response.ApiResponse
import com.xudre.marvelheroes.service.response.BaseResponse
import com.xudre.marvelheroes.service.response.CharacterResponse
import com.xudre.marvelheroes.service.response.ComicResponse
import com.xudre.marvelheroes.util.Paged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelRepositoryImpl(val service: ApiService) : MarvelRepository {

    private val resources = Resources.getSystem()

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
                        } ?: finished(Throwable(resources.getString(R.string.error_api_generic)))
                    } else {
                        finished(errorTreatment(response as Response<ApiResponse<BaseResponse>>))
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
                            val count = it.data.count.toLong()
                            val items = mutableListOf<ComicBookModel>()
                            val onPage = if (count > 0) it.data.offset.toLong() / count else 0

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
                        } ?: finished(Throwable(resources.getString(R.string.error_api_generic)))
                    } else {
                        finished(errorTreatment(response as Response<ApiResponse<BaseResponse>>))
                    }
                }

                fun finished(throwable: Throwable? = null) {
                    throwable?.let(onFail)

                    onFinish()
                }
            })
    }

    private fun errorTreatment(response: Response<ApiResponse<BaseResponse>>): Throwable {
        when (response.code()) {
            401 -> return Throwable(resources.getString(R.string.error_api_invalid_hash_or_key))
            403 -> return Throwable(resources.getString(R.string.error_endpoint_inaccessible))
            409 -> return Throwable(response.message())
        }

        return Throwable(resources.getString(R.string.error_api_generic))
    }

}
