package com.xudre.marvelheroes.config

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.MainActivityViewModel
import com.xudre.marvelheroes.repository.MarvelRepository
import com.xudre.marvelheroes.repository.MarvelRepositoryImpl
import com.xudre.marvelheroes.service.ApiService
import com.xudre.marvelheroes.service.AuthInterceptor
import com.xudre.marvelheroes.ui.character.CharacterViewModel
import com.xudre.marvelheroes.ui.characterlist.CharacterListAdapter
import com.xudre.marvelheroes.ui.characterlist.CharacterListViewModel
import com.xudre.marvelheroes.ui.comicbook.ComicBookViewModel
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val networkModule = module {
    // Retrofit
    factory { AuthInterceptor() }
    factory { ApiConfig.URL }
    factory { buildHttpClient(get()) }
    single { buildRetrofit(get(), get()).create(ApiService::class.java) }
    // Picasso
    single { buildPicasso(get(), get()) }
}

val repositoryModule = module {
    single<MarvelRepository> { MarvelRepositoryImpl(get()) }
}

val adapterModule = module {
    single { CharacterListAdapter(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { CharacterListViewModel(get()) }
    viewModel { CharacterViewModel(get()) }
    viewModel { ComicBookViewModel(get()) }
}

private fun buildHttpClient(interceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .addInterceptor(interceptor)
        .build()
}

private fun buildRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun buildPicasso(context: Context, client: OkHttpClient): Picasso {
    return Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build();
}
