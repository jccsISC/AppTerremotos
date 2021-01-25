package com.jccsisc.earthaquakemonitor.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jccsisc.earthaquakemonitor.data.model.EarthquakeModel
import com.jccsisc.earthaquakemonitor.data.model.EqJsonResponse
import com.jccsisc.earthaquakemonitor.repository.MainRepository
import com.jccsisc.earthaquakemonitor.service
import kotlinx.coroutines.*

class MainviewModel: ViewModel() {
    private var _eqList = MutableLiveData<MutableList<EarthquakeModel>>()
    val eqList: LiveData<MutableList<EarthquakeModel>>
    get() = _eqList

    private val repo = MainRepository()

    init {
        viewModelScope.launch {
            _eqList.value = repo.fetchEartquakes()
        }
    }
}