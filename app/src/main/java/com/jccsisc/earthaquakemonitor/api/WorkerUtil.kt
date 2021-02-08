package com.jccsisc.earthaquakemonitor.api

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkerUtil {

    fun scheduleSync(context: Context) {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)//solo se va a activar el workerManager si está conectado a internet
            .build()

        val syncRequest =
            PeriodicWorkRequestBuilder<SyncWorkManager>(1, TimeUnit.HOURS)
                .setConstraints(constraint)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, syncRequest) //cada vez que abrmoz la app ignora
    }
}