package com.jccsisc.earthaquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val application: Application, private val sortType: Boolean): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainviewModel(application, sortType) as T
        Log.d("sortType", "$sortType factory")
    }
}