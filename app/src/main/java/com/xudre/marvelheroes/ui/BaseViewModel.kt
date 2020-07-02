package com.xudre.marvelheroes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    val loadingState = MutableLiveData<Boolean>(false)

    val errorState = MutableLiveData<Throwable?>(null)

}
