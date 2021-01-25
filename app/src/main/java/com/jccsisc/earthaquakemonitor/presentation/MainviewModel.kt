package com.jccsisc.earthaquakemonitor.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jccsisc.earthaquakemonitor.data.model.EarthquakeModel
import com.jccsisc.earthaquakemonitor.service
import kotlinx.coroutines.*
import org.json.JSONObject

class MainviewModel: ViewModel() {
//    private val job = Job()
//    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private var _eqList = MutableLiveData<MutableList<EarthquakeModel>>()
    val eqList: LiveData<MutableList<EarthquakeModel>>
    get() = _eqList

    init {
        viewModelScope.launch {
            _eqList.value = fetchEartquakes()
        }
    }

    private suspend fun fetchEartquakes(): MutableList<EarthquakeModel> {
        return withContext(Dispatchers.IO) {
            val eqListString = service.getLastHourEartquakes() //obtenemos los datos del servicio
            val eqList = parseEqResult(eqListString)

            //lo ultimo en la corutina es lo que devuelv
            eqList
        }
    }

    private fun parseEqResult(eqListString: String): MutableList<EarthquakeModel> {
        //convertiremos el string en un Json
        val eqJsonObject = JSONObject(eqListString)
        val featuresJsonArray = eqJsonObject.getJSONArray("features")

        val eqList = mutableListOf<EarthquakeModel>() //lista vacia

        //como es un array tenemos que iterar entre sus elementos.
        for (i in 0 until featuresJsonArray.length()) {
            val featuresJonObject = featuresJsonArray[i] as JSONObject
            val id = featuresJonObject.getString("id") //vemos que el id es de tipo String

            //como vemos la magnitud el lugar y el tiempo estan dentro de otro array object
            val propertiesJsonObject = featuresJonObject.getJSONObject("properties")
            val magnitude = propertiesJsonObject.getDouble("mag")
            val place = propertiesJsonObject.getString("place")
            val time = propertiesJsonObject.getLong("time")

            val geometryJsonObjet = featuresJonObject.getJSONObject("geometry")
            //estan dentro de un array de elementos
            val coordinatesJsonArray = geometryJsonObjet.getJSONArray("coordinates")
            val longitude = coordinatesJsonArray.getDouble(0)
            val latitude = coordinatesJsonArray.getDouble(1)

            //ahora si podemos crear los terremotos
            val earthquakeModel = EarthquakeModel(id, place, magnitude, time, longitude, latitude)
            eqList.add(earthquakeModel)
        }

        return eqList
    }


//    override fun onCleared() {
//        super.onCleared()
//        job.cancel()
//    }
}