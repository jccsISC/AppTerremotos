package com.jccsisc.earthaquakemonitor.main

import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.api.EqJsonResponse
import com.jccsisc.earthaquakemonitor.api.service
import com.jccsisc.earthaquakemonitor.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//le pasamos la base de datos
class MainRepository(private val dataBase: EqDataBase) {

    suspend fun fetchEartquakes(sortByMagnitude: Boolean): MutableList<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            val eqJsonResponse = service.getLastHourEartquakes() //obtenemos los datos del servicio
            val eqList = parseEqResult(eqJsonResponse)

            //como ya tenemos los datos ahora hay que guardarlos en la db
            dataBase.eqDao.insertAll(eqList)//insertamos los valores

            fetchEartquakesFromDb(sortByMagnitude)
        }
    }

    //trallendo datos desde la base de datos room
    suspend fun fetchEartquakesFromDb(sortByMagnitude: Boolean): MutableList<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                dataBase.eqDao.getEarthquakesByMagnitude()
            } else {
                dataBase.eqDao.getEarthquakes()
            }
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