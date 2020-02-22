package app.mobile.carlist.cars.data

import app.mobile.carlist.api.ApiService
import app.mobile.carlist.api.BaseDataSource
import javax.inject.Inject

class CarRemoteDataSource @Inject constructor(private val service: ApiService) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getCars() }

}
