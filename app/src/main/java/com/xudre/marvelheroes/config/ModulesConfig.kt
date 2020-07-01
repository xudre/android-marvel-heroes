package com.xudre.marvelheroes.config

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.xudre.marvelheroes.repository.MarvelRepository
import com.xudre.marvelheroes.repository.MarvelRepositoryImpl
import com.xudre.marvelheroes.service.ApiService
import com.xudre.marvelheroes.service.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

private fun buildHttpClient(interceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
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
