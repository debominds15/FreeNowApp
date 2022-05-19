package com.freenow.app.presentation.vehicle_list.components

import com.freenow.app.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Map : NavigationItem("music", R.drawable.ic_baseline_location_on_24, "Map")
}
