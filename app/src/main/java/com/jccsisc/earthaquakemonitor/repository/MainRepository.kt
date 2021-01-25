package com.jccsisc.earthaquakemonitor.repository

import com.jccsisc.earthaquakemonitor.data.model.EarthquakeModel
import com.jccsisc.earthaquakemonitor.data.model.EqJsonResponse
import com.jccsisc.earthaquakemonitor.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {

    suspend fun fetchEartquakes(): MutableList<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            val eqJsonResponse = service.getLastHourEartquakes() //obtenemos los datos del servicio
            val eqList = parseEqResult(eqJsonResponse)

            //lo ultimo en la corutina es lo que devuelv
            eqList
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<EarthquakeModel> {

        val eqList = mutableListOf<EarthquakeModel>() //lista vacia
        val featuresList = eqJsonResponse.features

        for (feature in featuresList) {
            val id = feature.id

            val properties = feature.properties
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(EarthquakeModel(id, place, magnitude, time, longitude, latitude))
        }

        return eqList
    }
}