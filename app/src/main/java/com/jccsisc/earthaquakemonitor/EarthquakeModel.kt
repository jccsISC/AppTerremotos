package com.jccsisc.earthaquakemonitor

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_eartquakes")
data class EarthquakeModel(
    @PrimaryKey
    val id: String,
    val place: String,
    val magnintude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double
)