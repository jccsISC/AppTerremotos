package com.jccsisc.earthaquakemonitor.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface EqApiService {
    //aqui todos los request que queramos

    @GET("all_hour.geojson")
    suspend fun getLastHourEartquakes(): EqJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: EqApiService = retrofit.create(EqApiService::class.java)