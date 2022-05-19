package com.freenow.app.domain.use_case.get_vehicles

import com.freenow.app.common.Resource
import com.freenow.app.data.remote.dto.toVehicle
import com.freenow.app.domain.model.Vehicle
import com.freenow.app.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    operator fun invoke(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Flow<Resource<List<Vehicle>>> = flow {
        try {
            emit(Resource.Loading<List<Vehicle>>())
            val vehicles = repository.getVehicles(lat1,lon1, lat2, lon2).poiList.map { it.toVehicle() }
            emit(Resource.Success<List<Vehicle>>(vehicles))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Vehicle>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Vehicle>>("Couldn't reach server. Check your internet connection and try again."))
        }
    }
}