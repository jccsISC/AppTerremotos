package com.jccsisc.earthaquakemonitor.main

import androidx.lifecycle.LiveData
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.api.EqJsonResponse
import com.jccsisc.earthaquakemonitor.api.service
import com.jccsisc.earthaquakemonitor.database.EqDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//le pasamos la base de datos
class MainRepository(private val dataBase: EqDataBase) {

    val eqList: LiveData<MutableList<EarthquakeModel>> = dataBase.eqDao.getEarthquakes()

    suspend fun fetchEartquakes() {
        return withContext(Dispatchers.IO) {
            val eqJsonResponse = service.getLastHourEartquakes() //obtenemos los datos del servicio
            val eqList = parseEqResult(eqJsonResponse)

            //como ya tenemos los datos ahora hay que guardarlos en la db
            dataBase.eqDao.inserAll(eqList)//insertamos los valores
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