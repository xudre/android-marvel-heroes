package com.xudre.marvelheroes.ui.character

import androidx.lifecycle.MutableLiveData
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.repository.MarvelRepository
import com.xudre.marvelheroes.ui.BaseViewModel

class CharacterViewModel(private val marvelRepository: MarvelRepository) : BaseViewModel() {

    val character = MutableLiveData<CharacterModel>()

}
