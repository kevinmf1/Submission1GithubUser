package com.kevinmedia.submission1githubuser.viewModels

import android.os.Build
import androidx.lifecycle.Observer
import com.kevinmedia.submission1githubuser.GithubUser
import com.kevinmedia.submission1githubuser.api.APIService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var mockApiService: APIService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        mockApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))//We will use MockWebServers url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        viewModel = MainViewModel()
    }

    private fun getJson(path: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(path)
        val bufferedReader = inputStream?.bufferedReader()
        return bufferedReader?.use { it.readText() } ?: ""
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getDataUserSearch_success() {
        // get json string from file
        val jsonString = getJson("com/kevinmedia/submission1githubuser/json/ImageResponse.json")

        // create mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonString)
        val expected = mockWebServer.enqueue(mockResponse)

        val results = viewModel.getDataUserSearch("KeVinmf1")

        // wait for API response
        Thread.sleep(1000)

        // assert equals
        assertEquals(expected, results)
    }

    @Test
    fun getDataUserSearch_failure() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))

        val observer = Observer<List<GithubUser>> {}
        viewModel.listUser.observeForever(observer)

        viewModel.getDataUserSearch("KeVinmf1")

        // wait for API response
        Thread.sleep(1000)

        // verify that listUser does not emit any value
        assertTrue(viewModel.listUser.value.isNullOrEmpty())

        // clean up
        viewModel.listUser.removeObserver(observer)
    }
}

