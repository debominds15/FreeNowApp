package com.freenow.app.domain.repository

import com.freenow.app.data.remote.dto.PoiDto

interface VehicleRepository {
    suspend fun getVehicles(p1Lat: Double,
                            p1Lon: Double,
                            p2Lat: Double,
                            p2Lon: Double): PoiDto
}