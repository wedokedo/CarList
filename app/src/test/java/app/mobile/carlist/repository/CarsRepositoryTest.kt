package app.mobile.carlist.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.mobile.carlist.api.ApiService
import app.mobile.carlist.cars.data.Car
import app.mobile.carlist.cars.data.CarDao
import app.mobile.carlist.cars.data.CarRemoteDataSource
import app.mobile.carlist.cars.data.CarRepository
import app.mobile.carlist.data.AppDatabase
import app.mobile.carlist.observforever

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import app.mobile.carlist.data.Result


@RunWith(JUnit4::class)
class CarsRepositoryTest {
    private lateinit var repository: CarRepository
    private val dao = mock(CarDao::class.java)
    private val service = mock(ApiService::class.java)
    private val remoteDataSource = CarRemoteDataSource(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.carDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = CarRepository(dao, remoteDataSource)
    }

    @Test
    fun loadCarsFromNetwork() {
        runBlocking {
            repository.getCars().observforever(Observer<Result<List<Car>>> {
                 it.data?.let {
                     Assert.assertThat(it.count(), CoreMatchers.`is`(9))
                 }


            })
            verify(dao, never()).getCars()
            verifyZeroInteractions(dao)
        }
    }

}

