package com.jccsisc.earthaquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.api.StatusResponse
import com.jccsisc.earthaquakemonitor.database.getDatabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainviewModel::class.java.simpleName
//le pasamos el valor Aplication para poder heredar un contexto
class MainviewModel(application: Application): AndroidViewModel(application) {

    private val dataBase = getDatabase(application) //creamos la base de datos
    private val repo = MainRepository(dataBase)

    private val _status = MutableLiveData<StatusResponse>()
    val status: LiveData<StatusResponse>
    get() = _status

    private val _eqList = MutableLiveData<MutableList<EarthquakeModel>>()
    val eqList: LiveData<MutableList<EarthquakeModel>>
    get() = _eqList

    init {
        reloadEarthquakes(false)
    }

    private fun reloadEarthquakes(sortByMagnitude: Boolean) {
        viewModelScope.launch {
            try {
                _status.value = StatusResponse.LOADING
                _eqList.value = repo.fetchEartquakes(sortByMagnitude)
                _status.value = StatusResponse.DONE
            } catch (e: UnknownHostException) {
                _status.value = StatusResponse.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No internet connection $e")
            }
        }
    }

    fun reloadEarthquakesFromDb(sortByMagnitude: Boolean) {
        viewModelScope.launch {
           _eqList.value = repo.fetchEartquakesFromDb(sortByMagnitude)
        }
    }
}