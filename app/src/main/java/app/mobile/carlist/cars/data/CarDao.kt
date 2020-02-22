package app.mobile.carlist.cars.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mobile.carlist.cars.data.Car

@Dao
interface CarDao {

    @Query("SELECT * FROM cars")
    fun getCars(): LiveData<List<Car>>
    @Query("DELETE FROM cars")
    suspend fun clearCars()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(plants: List<Car>)
}