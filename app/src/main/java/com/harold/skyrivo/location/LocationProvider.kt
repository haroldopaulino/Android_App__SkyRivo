package com.harold.skyrivo.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

interface LocationProvider {
    suspend fun currentLocation(): Pair<Double, Double>?
}

class AndroidLocationProvider(context: Context) : LocationProvider {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun currentLocation(): Pair<Double, Double>? {
        val location = client.lastLocation.await() ?: return null
        return location.latitude to location.longitude
    }
}
