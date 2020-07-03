package com.xudre.marvelheroes.ui.characterlist

import androidx.lifecycle.MutableLiveData
import com.xudre.marvelheroes.config.AppConfig
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.repository.MarvelRepository
import com.xudre.marvelheroes.ui.BaseViewModel
import com.xudre.marvelheroes.util.Paged

class CharacterListViewModel(private val marvelRepository: MarvelRepository) : BaseViewModel() {

    val characters = MutableLiveData<Paged<CharacterModel>>()

    fun getCharacters(page: Number = 0, perPage: Number = AppConfig.PER_PAGE) {
        if (loadingState.value == true) return

        loadingState.value = true

        marvelRepository.getCharacters(page, perPage, { paged ->
            characters.value = paged
        }, {
            errorState.value = it
        }, {
            loadingState.value = false
        })
    }
}
