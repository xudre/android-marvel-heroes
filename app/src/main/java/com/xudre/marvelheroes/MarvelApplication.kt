package com.xudre.marvelheroes

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.xudre.marvelheroes.config.networkModule
import com.xudre.marvelheroes.config.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelApplication)
            modules(listOf(
                networkModule,
                repositoryModule
            ))
        }

        appContext = this
    }

    companion object {
        lateinit var appContext: Context

        fun notify(message: String) {
            Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}
