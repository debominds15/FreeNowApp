package com.freenow.app.util

import com.freenow.app.common.Constants
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/*
* This utility class holds the function to calculate the distance between the two points
* https://cloud.google.com/blog/products/maps-platform/how-calculate-distances-map-maps-javascript-api
*/

object CommonUtils {
    fun findDistance(lat: Double, lon: Double): Double {
        // Radius of the Earth in miles
        val rad = 3958.8
        // Convert degrees to radians
        val lat1 = lat * (Math.PI / 180)
        // Convert degrees to radians
        val lat2 = Constants.VAL_P1_LAT * (Math.PI / 180)
        // Radian difference (latitudes)
        val diffLat = lat2 - lat1
        // Radian difference (longitudes)
        val diffLon = (lon - Constants.VAL_P1_LON) * (Math.PI / 180)

        val actualDistance = 2 * rad * asin(
            sqrt(
                sin(diffLat / 2) * sin(diffLat / 2) + cos(lat1) * cos(lat2) * sin(diffLon / 2) * sin(
                    diffLon / 2
                )
            )
        )
        return String.format("%.2f", actualDistance).toDouble()
    }
}