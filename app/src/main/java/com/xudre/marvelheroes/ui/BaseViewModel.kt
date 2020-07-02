package com.xudre.marvelheroes.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xudre.marvelheroes.util.ConnectivityLiveData

abstract class BaseViewModel: ViewModel() {

    private var connectivityLiveData: ConnectivityLiveData? = null

    val loadingState = MutableLiveData<Boolean>(false)

    val errorState = MutableLiveData<Throwable?>(null)

    fun connectivityState(context: Context): ConnectivityLiveData {
        if (connectivityLiveData == null) {
            connectivityLiveData = ConnectivityLiveData(context)
        }

        return connectivityLiveData!!
    }

}
