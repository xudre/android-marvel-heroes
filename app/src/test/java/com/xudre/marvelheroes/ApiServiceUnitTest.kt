package com.xudre.marvelheroes

import com.xudre.marvelheroes.config.ApiConfig
import com.xudre.marvelheroes.extension.hash
import com.xudre.marvelheroes.service.ApiService
import com.xudre.marvelheroes.service.AuthInterceptor
import com.xudre.marvelheroes.service.response.CharacterComicsResponse
import com.xudre.marvelheroes.service.response.CharacterResponse
import com.xudre.marvelheroes.service.response.ComicResponse
import com.xudre.marvelheroes.service.response.ImageResponse
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.HttpURLConnection
import java.util.*
import kotlin.math.roundToInt

class ApiServiceUnitTest {

    private val server = MockWebServer()

    private lateinit var client: OkHttpClient

    private lateinit var service: ApiService

    @Before
    fun start() {
        server.start()

        client = OkHttpClient().newBuilder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .addInterceptor(AuthInterceptor())
            .build()

        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun request_validateHash() {
        val response = responseSuccessful("{}")

        server.enqueue(response)

        val timestamp = (Calendar.getInstance().timeInMillis / 1000f).roundToInt()
        val expectedHash = "$timestamp${ApiConfig.SECRET}${ApiConfig.KEY}".hash(ApiConfig.DIGEST)

        service.getCharacters("name", 0, 10).execute()

        val request = server.takeRequest()
        val requestHash = request.requestUrl?.queryParameter("hash")

        assertEquals(expectedHash, requestHash)
    }

    @Test
    fun request_charactersList() {
        val body = File("sampledata\\characters-list.json").bufferedReader().use { it.readText() }
        val response = responseSuccessful(body)

        server.enqueue(response)

        val mockCharacterResponse = CharacterResponse(
            1009144,
            "A.I.M.",
            "AIM is a terrorist organization bent on destroying the world.",
            ImageResponse(
                "http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec",
                "jpg"
            ),
            CharacterComicsResponse(
                49
            )
        )

        val result = service.getCharacters("name", 0, 10).execute()
        val resultItems = result.body()?.data?.results!!
        val resultItem = resultItems[2]

        assertEquals(mockCharacterResponse.name, resultItem.name)
    }

    @Test
    fun request_characterComicBooks() {
        val body = File("sampledata\\character-comic-books.json").bufferedReader().use { it.readText() }
        val response = responseSuccessful(body)

        server.enqueue(response)

        val mockComicResponse = ComicResponse(
            27812,
            "Agents of Atlas (2009) #7 (70S DECADE VARIANT)",
            "Journey to the Deep!!\r\nThe Agents and the Sub-Mariner face not only a deadly attack beneath the ocean, but Namor and Namora's budding relationship is hit with some ugly facts of Atlantean history. Also: what does a Lung Dragon dream? Peer into the most treacherous space imaginable, the ancient and diabolical mind of Mr. Lao!\r\nRated T  ...$2.99",
            ImageResponse(
                "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bb45670ca39d",
                "jpg"
            ),
            listOf()
        )

        val result = service.getCharacterComics(1011198, "-onsaleDate", 0, 10).execute()
        val resultItems = result.body()?.data?.results!!
        val resultItem = resultItems.firstOrNull { item -> item.id.toInt() == mockComicResponse.id.toInt() }

        assertNotNull(resultItem)
    }

    @After
    fun finish() {
        server.shutdown()
    }

    private fun responseSuccessful(body: String): MockResponse {
        return MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
    }
}
