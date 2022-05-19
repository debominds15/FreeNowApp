package com.freenow.app.domain.model

import com.freenow.app.data.remote.dto.Coordinate

data class Vehicle(
    val id: Long,
    val coordinate: Coordinate,
    val fleetType: String,
    val heading: Double,
    var distance: Double = 0.0
)
