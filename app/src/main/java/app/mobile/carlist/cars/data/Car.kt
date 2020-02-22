package app.mobile.carlist.cars.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cars")
data class Car(
    @PrimaryKey
    @field:SerializedName("make")
    val make: String,
    @field:SerializedName("model")
    val model: String ,
    @field:SerializedName("price")
    val price: String,
    @field:SerializedName("year")
    val year: String,
    @field:SerializedName("km")
    val km: String,
    @field:SerializedName("picture")
    val picture: String


) : Parcelable {
    override fun toString() = make
}
