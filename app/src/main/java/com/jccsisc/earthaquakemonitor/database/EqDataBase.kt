package com.jccsisc.earthaquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jccsisc.earthaquakemonitor.EarthquakeModel

//aqui le decimos todas las entidades que va a usar
//cada que agreguemos una funcion o quitemos una entidad tenemos que cambiar de versión
@Database(entities = [EarthquakeModel::class], version = 1)
abstract class EqDataBase: RoomDatabase() {
    abstract val eqDao: EqDao
}


//esto es muy repetitivo PATRON DE DISELO SINGLETON

private lateinit var INTANCE: EqDataBase

//necesita un contexto para poder crear la base de datos
fun getDatabase(context: Context): EqDataBase {
    synchronized(EqDataBase::class.java) {
        if (!::INTANCE.isInitialized) {
         INTANCE = Room.databaseBuilder(context.applicationContext, EqDataBase::class.java, "db_eartquakes").build()
        }
        return INTANCE
    }
}