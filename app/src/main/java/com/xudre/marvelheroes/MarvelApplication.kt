package com.xudre.marvelheroes

import android.app.Application
import com.xudre.marvelheroes.config.adapterModule
import com.xudre.marvelheroes.config.networkModule
import com.xudre.marvelheroes.config.repositoryModule
import com.xudre.marvelheroes.config.viewModelModule
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
                repositoryModule,
                adapterModule,
                viewModelModule
            ))
        }
    }
}
