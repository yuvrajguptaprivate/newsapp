package com.example.mynewsapiclient.data

import com.example.mynewsapiclient.BuildConfig
import com.example.mynewsapiclient.data.api.NewsAPIService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class newsapitest {
    private lateinit var servise : NewsAPIService
    private lateinit var mockWebServer: MockWebServer




    @Before
    fun setup(){
       mockWebServer = MockWebServer()
       servise = Retrofit.Builder()
           .baseUrl(mockWebServer.url(""))
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(NewsAPIService::class.java)
    }




    private fun enqueueMockResponse(
        filename : String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }


    @Test
    fun getTopheadline_sendrequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
         val responseBody =   servise.getTopHeadlines("us", page = 1, BuildConfig.API_Key)
        val request = mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=daa1bb784f254c46b17dc07883bb0ba3")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctpagesize(){
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
            val responseBody =   servise.getTopHeadlines("us", page = 1, BuildConfig.API_Key).body()
            val artistlist = responseBody!!.articles
            assertThat(artistlist.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctcontent(){
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
            val responseBody =   servise.getTopHeadlines("us", page = 1, BuildConfig.API_Key).body()
            val artistlist = responseBody!!.articles
            val articale = artistlist[0]
            assertThat(articale.title).isEqualTo("List of 'NFL Honors' award winners from 2024 NFL season - NFL.com")
        }
    }

    @After
    fun tearDown(){
        mockWebServer.close()
    }
}