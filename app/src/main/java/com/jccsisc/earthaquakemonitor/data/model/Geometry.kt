package com.jccsisc.earthaquakemonitor.data.model

class Geometry(private val coordinates: Array<Double>) {
    val longitude: Double
    get() = coordinates[0]

    val latitude: Double
    get() = coordinates[1]
}