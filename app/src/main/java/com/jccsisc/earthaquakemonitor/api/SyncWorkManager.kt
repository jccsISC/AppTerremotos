package com.jccsisc.earthaquakemonitor.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jccsisc.earthaquakemonitor.database.getDatabase
import com.jccsisc.earthaquakemonitor.main.MainRepository

class SyncWorkManager(appContext: Context, parameters: WorkerParameters): CoroutineWorker(appContext, parameters) {

    companion object {
        const val WORK_NAME = "SyncWorkManager"
    }

    private val dataBase = getDatabase(appContext) //creamos la base de datos
    private val repo = MainRepository(dataBase)

    override suspend fun doWork(): Result {
        repo.fetchEartquakes(true)

        return Result.success()
    }
}