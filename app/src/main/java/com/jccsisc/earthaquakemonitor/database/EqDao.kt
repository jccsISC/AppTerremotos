package com.jccsisc.earthaquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jccsisc.earthaquakemonitor.EarthquakeModel

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(eqList: MutableList<EarthquakeModel>)

    @Query("SELECT * FROM tbl_eartquakes")
    fun getEarthquakes(): LiveData<MutableList<EarthquakeModel>>



    //obtener terremotos cuya magnitud sea mayor a x
    @Query("SELECT * FROM tbl_eartquakes WHERE magnintude > :magnitude")
    fun getEarthquakesMagnitud(magnitude: Double): MutableList<EarthquakeModel>


    @Update
    fun updateEarthquake(vararg eq: EarthquakeModel)

    //borrar un terremodo
    @Delete
    fun deleteEarthquake(eq: EarthquakeModel)
    //borrar una lista de terremotos
    @Delete
    fun deleteListEarthquake(vararg eq: EarthquakeModel)

}