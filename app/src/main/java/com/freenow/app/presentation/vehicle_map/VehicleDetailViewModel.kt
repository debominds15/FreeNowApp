package com.freenow.app.presentation.vehicle_map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.freenow.app.common.Constants
import com.freenow.app.data.remote.dto.Coordinate
import com.freenow.app.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _vehicle = MutableLiveData<Vehicle>()
    val vehicle: LiveData<Vehicle> = _vehicle

    init {
        savedStateHandle.get<String>(Constants.PARAM_VEHICLE)?.let { vehicle ->
            saveData(vehicle)
        }
    }

    private fun saveData(vehicle: String) {
        val details = vehicle.split("[")
        val v = Vehicle(
            details[0].toLong(),
            Coordinate(details[1].toDouble(), details[2].toDouble()),
            details[3], 0.0
        )
        _vehicle.value = v
    }
}
