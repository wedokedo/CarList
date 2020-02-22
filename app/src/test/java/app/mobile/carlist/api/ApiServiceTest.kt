package app.mobile.carlist.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestCars() {
        runBlocking {
            enqueueResponse("cars.json")
            val resultResponse = service.getCars().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/cars.json"))
        }
    }

    @Test
    fun getCarsResponse() {
        runBlocking {
            enqueueResponse("cars.json")
            val resultResponse = service.getCars().body()
            resultResponse?.let {
                assertThat(it.count(), `is`(9))
            }

        }
    }


    @Test
    fun getCar() {
        runBlocking {
            enqueueResponse("cars.json")
            val resultResponse = service.getCars().body()
            resultResponse?.let {
                val car = it[0]
                assertThat(car.make, `is`("Mercedes-Benz"))
                assertThat(car.model, `is`("A 180"))
                assertThat(car.price, `is`("13600.00"))
                assertThat(car.year, `is`("2015"))
                assertThat(car.km, `is`("130000"))
                assertThat(car.picture, `is`("https://media.autoweek.nl/m/kmayxerb6txf_800.jpg"))
            }

        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
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
