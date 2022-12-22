package com.thepparat.newsclient.data.api

import com.google.common.truth.Truth
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
import retrofit2.create
import kotlin.text.Charsets.UTF_8

class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build().create()

    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadline_sendRequest_receivedExpected() {
        val path = "/v2/top-headlines?&country=us&page=1&apiKey=070df0b924544bb4af9989fac62c4b3a"
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val body = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()
            Truth.assertThat(body).isNotNull()
            Truth.assertThat(request.path).isEqualTo(path)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSized() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val body = service.getTopHeadlines("us", 1).body()
            val articleList = body?.articles
            Truth.assertThat(articleList?.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent() {
        val author = "Phil Mattingly, Kevin Liptak, Manu Raju, Kaitlan Collins"
        val title =
            "Biden and Zelensky planning to meet in Washington for Ukrainian president's first foreign trip since war began - CNN"
        val description =
            "President Joe Biden and Ukrainian President Volodymyr Zelensky are planning to meet at the White House on Wednesday, according to two sources familiar with the planning, in a Washington visit that is tentatively scheduled to include an address to a joint sess…"
        val url =
            "https://www.cnn.com/2022/12/20/politics/volodymyr-zelensky-washington-dc-visit/index.html"
        val urlToImage =
            "https://media.cnn.com/api/v1/images/stellar/prod/221217163722-volodymyr-zelensky-file.jpg?c=16x9&q=w_800,c_fill"
        val publishedAt = "2022-12-21T02:25:00Z"
        val content =
            "President Joe Biden and Ukrainian President Volodymyr Zelensky are planning to meet at the White House on Wednesday, according to two sources familiar with the planning, in a Washington visit that is… [+5642 chars]"
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val body = service.getTopHeadlines("us", 1).body()
            val articleList = body?.articles
            val article = articleList?.get(0)
            Truth.assertThat(article?.author).isEqualTo(author)
            Truth.assertThat(article?.title).isEqualTo(title)
            Truth.assertThat(article?.description).isEqualTo(description)
            Truth.assertThat(article?.url).isEqualTo(url)
            Truth.assertThat(article?.urlToImage).isEqualTo(urlToImage)
            Truth.assertThat(article?.publishedAt).isEqualTo(publishedAt)
            Truth.assertThat(article?.content).isEqualTo(content)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}