package com.freenow.app.di

import com.freenow.app.common.Constants
import com.freenow.app.data.remote.VehiclePoiApi
import com.freenow.app.data.repository.VehicleRepositoryImpl
import com.freenow.app.domain.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideVehicleApi(): VehiclePoiApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VehiclePoiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(api: VehiclePoiApi): VehicleRepository {
        return VehicleRepositoryImpl(api)
    }
}