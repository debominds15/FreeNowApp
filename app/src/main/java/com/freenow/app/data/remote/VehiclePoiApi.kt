package com.freenow.app.data.remote

import com.freenow.app.data.remote.dto.PoiDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VehiclePoiApi {
    @GET("/")
    suspend fun getVehicles(@Query("p1Lat") p1Lat: Double,
                            @Query("p1Lon") p1Lon: Double,
                            @Query("p2Lat") p2Lat: Double,
                            @Query("p2Lon") p2Lon: Double): PoiDto
}