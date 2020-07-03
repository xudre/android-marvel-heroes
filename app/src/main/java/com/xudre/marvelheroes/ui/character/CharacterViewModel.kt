package com.xudre.marvelheroes.ui.character

import androidx.lifecycle.MutableLiveData
import com.xudre.marvelheroes.model.CharacterModel
import com.xudre.marvelheroes.ui.BaseViewModel

class CharacterViewModel : BaseViewModel() {

    val character = MutableLiveData<CharacterModel>()

}
