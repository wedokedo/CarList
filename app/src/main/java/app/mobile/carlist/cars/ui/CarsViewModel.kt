package app.mobile.carlist.cars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import app.mobile.carlist.cars.data.CarRepository
import javax.inject.Inject

class CarsViewModel @Inject constructor(repository: CarRepository) : ViewModel() {
    val reloadTrigger = MutableLiveData<Boolean>()
    val cars = Transformations.switchMap(reloadTrigger) {
        repository.getCars()
    }

    init {
        refreshCars()
    }

    fun refreshCars() {
        reloadTrigger.value = true
    }
}