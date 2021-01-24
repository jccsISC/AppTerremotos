package com.jccsisc.earthaquakemonitor.data.model

data class EarthquakeModel(
    val id: String,
    val place: String,
    val magnintude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double
)