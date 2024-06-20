package com.example.final_project.home.Walking

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class WalkPageActivity {
    var prevLatLng: LatLng? = null
    var distance = 0
    var job: Job? = null

    fun start() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                // get location
                val currentLatLng = LatLng(3.0, 5.0)

                prevLatLng?.let {
                    distance = currentLatLng.betweenDistance(it)
                    prevLatLng = currentLatLng
                } ?: run {
                    prevLatLng = currentLatLng
                }

                delay(1000)
            }
        }
    }

    // onDestroy
    fun onDestroy() {
        job?.cancel()
    }
}
data class LatLng(
    val lat: Double,
    val lng: Double
)

object DistanceManager {

    private const val R = 6372.8 * 1000

    /**
     * 두 좌표의 거리를 계산한다.
     *
     * @param lat1 위도1
     * @param lon1 경도1
     * @param lat2 위도2
     * @param lon2 경도2
     * @return 두 좌표의 거리(m)
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }
}

fun LatLng.betweenDistance(target: LatLng) = DistanceManager.getDistance(lat, lng, target.lat, target.lng)