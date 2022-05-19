package com.freenow.app.presentation.vehicle_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.freenow.app.R
import com.freenow.app.presentation.vehicle_map.InitialZoom
import com.freenow.app.presentation.vehicle_map.MaxZoom
import com.freenow.app.presentation.vehicle_map.TiltLevel
import com.freenow.app.util.TestTags.VEHICLES_MAP_SECTION
import com.freenow.app.util.bitmapDescriptorFromVector
import com.freenow.app.util.rememberMapViewWithLifeCycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun VehicleListMapScreen(
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val map = rememberMapViewWithLifeCycle()
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
            .testTag(VEHICLES_MAP_SECTION)
    ) {
        AndroidView({ map }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true

                val builder = LatLngBounds.Builder()
                state.vehicles.forEach { vehicle ->
                    val position = LatLng(vehicle.coordinate.latitude, vehicle.coordinate.longitude)
                    builder.include(position)
                    val icon = bitmapDescriptorFromVector(
                        context,
                        if (vehicle.fleetType == "POOLING") R.drawable.ic_baseline_directions_car_24 else R.drawable.ic_baseline_local_taxi_24
                    )

                    val markerOptions = MarkerOptions()
                        .title("Id: ${vehicle.id}")
                        .position(position)
                        .icon(icon)


                    map.addMarker(markerOptions)
                    map.setOnMarkerClickListener {
                        val cameraPosition = CameraPosition.Builder()
                            .target(it.position)
                            .zoom(InitialZoom)
                            .bearing(it.rotation)
                            .tilt(TiltLevel)
                            .build()
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        it.showInfoWindow()
                        true
                    }
                }

                if (state.vehicles.isNotEmpty()) {
                    val bounds = builder.build()
                    val width: Int = context.resources.displayMetrics.widthPixels
                    val height: Int = context.resources.displayMetrics.heightPixels
                    val padding =
                        (width * 0.10).toInt() // offset from edges of the map 10% of screen
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
                    map.moveCamera(cu)
                    map.animateCamera(cu)
                }

            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}
