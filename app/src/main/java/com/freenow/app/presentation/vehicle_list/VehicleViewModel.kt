package com.freenow.app.presentation.vehicle_list


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freenow.app.common.Constants.VAL_P1_LAT
import com.freenow.app.common.Constants.VAL_P1_LON
import com.freenow.app.common.Constants.VAL_P2_LAT
import com.freenow.app.common.Constants.VAL_P2_LON
import com.freenow.app.common.Resource
import com.freenow.app.domain.use_case.get_vehicles.GetVehiclesUseCase
import com.freenow.app.util.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(VehicleListState())
    val state: State<VehicleListState> = _state

    init {
        getVehicles()
    }

    private fun getVehicles() {
        getVehiclesUseCase(VAL_P1_LAT, VAL_P1_LON, VAL_P2_LAT, VAL_P2_LON).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.forEach {
                        it.distance = CommonUtils.findDistance(
                            it.coordinate.latitude,
                            it.coordinate.longitude
                        )
                    }
                    _state.value = VehicleListState(vehicles = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = VehicleListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = VehicleListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}