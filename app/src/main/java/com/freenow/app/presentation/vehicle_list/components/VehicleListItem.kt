package com.freenow.app.presentation.vehicle_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freenow.app.R
import com.freenow.app.domain.model.Vehicle


@Composable
fun VehicleListItems(
    vehicle: Vehicle,
    onItemClick: (Vehicle) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, Color(R.color.purple_700)),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(vehicle) }
                .padding(20.dp)
        ) {
            VehicleImage(vehicle)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(CenterVertically)
            ) {
                Text(
                    text = vehicle.fleetType,
                    style = MaterialTheme.typography.h6,
                    color = Color(R.color.purple_700)
                )
                Text(
                    text = "Id: ${vehicle.id}",
                    style = MaterialTheme.typography.caption,
                    color = Color(R.color.purple_700)
                )
                Text(
                    text = "Distance: ${vehicle.distance} miles",
                    style = MaterialTheme.typography.caption,
                    color = Color(R.color.purple_700)
                )
                Text(
                    text = "Heading: ${vehicle.heading}",
                    style = MaterialTheme.typography.caption,
                    color = Color(R.color.purple_700)
                )

            }
        }
    }
}

@Composable
fun VehicleImage(vehicle: Vehicle) {
    Image(
        painter = painterResource(
            id = if (vehicle.fleetType == "POOLING") R.drawable.ic_baseline_directions_car_24
            else R.drawable.ic_baseline_local_taxi_24
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}