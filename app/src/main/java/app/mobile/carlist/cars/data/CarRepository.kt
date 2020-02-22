package app.mobile.carlist.cars.data

import app.mobile.carlist.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepository @Inject constructor(private val dao: CarDao,
                                        private val remoteSource: CarRemoteDataSource) {

    fun getCars()=resultLiveData(
        databaseQuery = { dao.getCars() },
        networkCall = { remoteSource.fetchData() },
        clearCars = { dao.clearCars() },
        saveCallResult = { dao.insertCars(it) })

}