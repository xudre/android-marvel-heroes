package com.xudre.marvelheroes.ui.comicbook

import androidx.lifecycle.MutableLiveData
import com.xudre.marvelheroes.config.AppConfig
import com.xudre.marvelheroes.model.ComicBookModel
import com.xudre.marvelheroes.repository.MarvelRepository
import com.xudre.marvelheroes.ui.BaseViewModel

class ComicBookViewModel(private val marvelRepository: MarvelRepository) : BaseViewModel() {

    val comics = MutableLiveData<List<ComicBookModel>>()

    val expensiveComic = MutableLiveData<ComicBookModel>()

    fun getComicsFromCharacter(
        characterId: Number,
        page: Number = 0,
        perPage: Number = AppConfig.PER_PAGE
    ) {
        if (loadingState.value == true) return

        marvelRepository.getComicsFromCharacter(characterId, page, perPage, { paged ->
            val list = paged?.items?.sortedByDescending { it.price.toDouble() } ?: listOf()

            comics.value = list

            expensiveComic.value = list.first()
        }, {
            errorState.value = it
        }, {
            loadingState.value = false
        })
    }

}
