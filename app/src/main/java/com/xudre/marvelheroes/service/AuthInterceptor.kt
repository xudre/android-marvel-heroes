package com.xudre.marvelheroes.service

import com.xudre.marvelheroes.MarvelApplication
import com.xudre.marvelheroes.config.ApiConfig
import com.xudre.marvelheroes.extension.hash
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = signedRequest(chain.request())
        val response = chain.proceed(request)

        when (response.code()) {
            401 -> MarvelApplication.notify("Não autorizado.")
        }

        return response
    }

    private fun signedRequest(request: Request): Request {
        val now = Calendar.getInstance()
        val timestamp = now.timeInMillis.toString()
        val hash = "$timestamp${ApiConfig.SECRET}${ApiConfig.KEY}".hash(ApiConfig.DIGEST)
        val url = request.url()
            .newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("hash", hash)
            .addQueryParameter("apiKey", ApiConfig.KEY)
            .build()

        return request.newBuilder()
            .url(url)
            .build()
    }
}
