package com.jccsisc.earthaquakemonitor.main

import android.app.Application
import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.*
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.api.StatusResponse
import com.jccsisc.earthaquakemonitor.database.getDatabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainviewModel::class.java.simpleName
//le pasamos el valor Aplication para poder heredar un contexto
class MainviewModel(application: Application, private val sortType: Boolean): AndroidViewModel(application) {

    private val dataBase = getDatabase(application) //creamos la base de datos
    private val repo = MainRepository(dataBase)

    private val _status = MutableLiveData<StatusResponse>()
    val status: LiveData<StatusResponse>
    get() = _status

    private val _eqList = MutableLiveData<MutableList<EarthquakeModel>>()
    val eqList: LiveData<MutableList<EarthquakeModel>> //creamos el liveDta para poder sguir observando desde el main
    get() = _eqList

    init {
        reloadEarthquakes() //siempre lo va a ordenar por tiempo por defecto
    }

    private fun reloadEarthquakes() {
        viewModelScope.launch {
            try {
                _status.value = StatusResponse.LOADING
                _eqList.value = repo.fetchEartquakes(sortType)
                Log.d("sortType", "$sortType ViewModel")
                _status.value = StatusResponse.DONE
            } catch (e: UnknownHostException) {
                _status.value = StatusResponse.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No internet connection $e")
            }
        }
    }

    //observando los cambios de la base de datos
    fun reloadEarthquakesFromDb(sortByMagnitude: Boolean) {
        viewModelScope.launch {
           _eqList.value = repo.fetchEartquakesFromDb(sortByMagnitude)
        }
    }
}