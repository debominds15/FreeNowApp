package com.freenow.app.presentation.vehicle_map

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.freenow.app.R
import com.freenow.app.util.bitmapDescriptorFromVector
import com.freenow.app.util.rememberMapViewWithLifeCycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

const val InitialZoom = 15f
const val MinZoom = 2f
const val MaxZoom = 20f
const val TiltLevel = 0f

@Composable
fun VehicleDetailMapScreen(
    viewModel: VehicleDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val map = rememberMapViewWithLifeCycle()
    val vehicle = viewModel.vehicle.value
    val cameraPosition = remember {
        LatLng(vehicle?.coordinate?.latitude ?: 0.0, vehicle?.coordinate?.longitude ?: 0.0)
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        val icon = bitmapDescriptorFromVector(
            context,
            if (vehicle?.fleetType == "POOLING") R.drawable.ic_baseline_directions_car_24 else R.drawable.ic_baseline_local_taxi_24
        )

        val markerOption = MarkerOptions().apply {
            title(vehicle?.fleetType)
            snippet("Id: ${vehicle?.id}")
            position(cameraPosition)
            icon(icon)
        }
        googleMap.apply {
            val marker =  addMarker(markerOption)
            marker?.showInfoWindow()
            moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))

           /* val cameraPosition = CameraPosition.Builder()
                .target(cameraPosition)
                .zoom(InitialZoom)
                .bearing(marker?.rotation ?: 0f)
                .tilt(TiltLevel)
                .build()
            animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
       */
        }
    }

    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }
    ZoomControls(zoom) {
        zoom = it.coerceIn(MinZoom, MaxZoom)
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        val mapZoom = zoom
        coroutineScope.launch {
            mapView.awaitMap().apply {
                setZoom(mapZoom)
                moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
            }
        }
    }
}

@Composable
fun ZoomControls(
    zoom: Float,
    onZoomChanged: (Float) -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ZoomButton("-", onClick = { onZoomChanged(zoom * 0.8f) })
        ZoomButton("+", onClick = { onZoomChanged(zoom * 1.2f) })
    }
}

@Composable
fun ZoomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

fun GoogleMap.setZoom(
    @FloatRange(from = MinZoom.toDouble(), to = MaxZoom.toDouble()) zoom: Float
) {
    resetMinMaxZoomPreference()
    setMinZoomPreference(zoom)
    setMaxZoomPreference(zoom)
}
