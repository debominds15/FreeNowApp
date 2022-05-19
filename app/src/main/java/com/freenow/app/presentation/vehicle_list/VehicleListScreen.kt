package com.freenow.app.presentation.vehicle_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenow.app.presentation.ScreenType
import com.freenow.app.presentation.vehicle_list.components.VehicleListItems
import com.freenow.app.util.TestTags

@Composable
fun VehicleListScreen(
    navController: NavController,
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize().testTag(TestTags.VEHICLES_SECTION)) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.vehicles) { vehicle ->
                VehicleListItems(
                    vehicle = vehicle,
                    onItemClick = {
                        //Toast.makeText(context, "$vehicle", Toast.LENGTH_SHORT).show()
                        navController.navigate(ScreenType.VehicleDetailScreen.route + "/${vehicle.id}[${vehicle.coordinate.latitude}[${vehicle.coordinate.longitude}[${vehicle.fleetType}")
                    }
                )
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
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}