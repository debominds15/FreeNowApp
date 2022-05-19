package com.freenow.app.data.repository

import com.freenow.app.data.remote.VehiclePoiApi
import com.freenow.app.data.remote.dto.PoiDto
import com.freenow.app.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val api: VehiclePoiApi
) : VehicleRepository {
    override suspend fun getVehicles(p1Lat: Double,
                                     p1Lon: Double,
                                     p2Lat: Double,
                                     p2Lon: Double): PoiDto {
        return api.getVehicles(p1Lat, p1Lon, p2Lat, p2Lon)
    }
}