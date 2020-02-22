package app.mobile.carlist.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.mobile.carlist.cars.data.Car
import app.mobile.carlist.cars.data.CarDao


@Database(entities = [Car::class],
version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "cars-db")
                .build()
        }
    }
}