package com.randomly.videos.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class TopGithubServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: TopGithubService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("https://github-trending-api.now.sh/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TopGithubService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun checkResponseNotNull() {
        runBlocking {
            enqueueResponse("trendingrepos.json")
            val resultResponse = service.getTrendingRepositories("java", "weekly").body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
        }
    }

    @Test
    fun checkResponseSize() {
        runBlocking {
            enqueueResponse("trendingrepos.json")
            val resultResponse = service.getTrendingRepositories("java", "weekly").body()


            assertThat(resultResponse?.size, `is`(25))
        }
    }


    @Test
    fun checkUser() {
        runBlocking {
            enqueueResponse("trendingrepos.json")
            val resultResponse = service.getTrendingRepositories("java", "weekly").body()
            val response = resultResponse?.get(0)

            assertThat(response?.username, `is`("natario1"))
            assertThat(response?.name, `is`("Mattia Iavarone"))

        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                ?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
                source.readString(Charsets.UTF_8))
        )
    }
}
