package com.xudre.marvelheroes.util

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.lifecycle.LiveData

class ConnectivityLiveData(private val context: Context) : LiveData<Boolean>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val callback = object : ConnectivityManager.NetworkCallback() {
        init {
            val network = connectivityManager.activeNetwork

            postValue(network != null)
        }

        override fun onAvailable(network: Network?) {
            postValue(evaluateNetwork(network))
        }

        override fun onLost(network: Network?) {
            postValue(false)
        }

        private fun evaluateNetwork(network: Network?): Boolean {
            if (network == null) return false

            val capabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(network)

            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.extras?.let {
                val network = it.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo?
                val hasConnectivity = network?.isConnectedOrConnecting ?: false

                postValue(hasConnectivity)
            }
        }
    }

    override fun onActive() {
        super.onActive()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            registerCallback()
        } else {
            subscribeService()
        }
    }

    override fun onInactive() {
        super.onInactive()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            unregisterCallback()
        } else {
            unsubscribeService()
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun registerCallback() {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    private fun unregisterCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun subscribeService() {
        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        context.registerReceiver(receiver, intent)
    }

    private fun unsubscribeService() {
        context.unregisterReceiver(receiver)
    }
}
