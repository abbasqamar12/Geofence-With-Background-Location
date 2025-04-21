package com.vmstechs.mygeofence17april2025

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

object LocationManager {

    private lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var callback: LocationCallback

    fun startLocationUpdates(context: Context) {
        fusedClient = LocationServices.getFusedLocationProviderClient(context)

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10_000).build()

        callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    Log.d("LocationManager", "Location Manager updated: ${location.latitude}, ${location.longitude}")
                    LocationViewModel.updateLocation(location)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
        }

        setupGeofencing(context)
    }

    fun stopLocationUpdates() {
        if (::fusedClient.isInitialized && ::callback.isInitialized) {
            fusedClient.removeLocationUpdates(callback)
        }
    }

    fun setupGeofencing(context: Context) {
        val geofence = Geofence.Builder()
            .setRequestId("MY_GEOFENCE_ID")
            .setCircularRegion(28.6270316, 77.3760845, 100f)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val intent = Intent(context, GeofenceReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val client = LocationServices.getGeofencingClient(context)
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            client.addGeofences(request, pendingIntent)
                .addOnSuccessListener {
                    Log.d("Geofence", "Geofence added")
                }
                .addOnFailureListener {
                    Log.e("Geofence", "Failed to add geofence: ${it.message}")
                }
        }
    }
}