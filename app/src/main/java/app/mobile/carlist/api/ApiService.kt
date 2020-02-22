package app.mobile.carlist.api

import app.mobile.carlist.cars.data.Car
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val ENDPOINT =
            "https://raw.githubusercontent.com/beckershoff/Egardia-Mobile-Development-Assessment/master/"
    }

    @GET("cars.json")
    suspend fun getCars(): Response<List<Car>>


}