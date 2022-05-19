package com.freenow.app.presentation.vehicle_list

import com.freenow.app.domain.model.Vehicle
import java.util.Collections.emptyList

data class VehicleListState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(),
    val error: String = ""
)
