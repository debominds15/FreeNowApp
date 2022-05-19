package com.freenow.app.data.remote.dto

import com.freenow.app.domain.model.Vehicle


data class VehicleDto(
    val id: Long,
    val coordinate: Coordinate,
    val fleetType: String,
    val heading: Double
)

fun VehicleDto.toVehicle(): Vehicle {
    return Vehicle(
        id = id,
        coordinate = coordinate,
        fleetType = fleetType,
        heading = heading
    )
}