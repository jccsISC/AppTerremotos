package com.jccsisc.earthaquakemonitor


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
@Entity(tableName = "tbl_eartquakes")
data class EarthquakeModel(
    @PrimaryKey
    val id: String = "",
    val place: String = "",
    val magnintude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(place)
        parcel.writeDouble(magnintude)
        parcel.writeLong(time)
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EarthquakeModel> {
        override fun createFromParcel(parcel: Parcel): EarthquakeModel {
            return EarthquakeModel(parcel)
        }

        override fun newArray(size: Int): Array<EarthquakeModel?> {
            return arrayOfNulls(size)
        }
    }
}