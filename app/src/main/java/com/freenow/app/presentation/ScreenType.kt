package com.freenow.app.presentation

sealed class ScreenType(val route: String) {
    object VehicleListScreen : ScreenType("vehicle_list_screen")
    object VehicleDetailScreen : ScreenType("vehicle_detail_screen")
}
