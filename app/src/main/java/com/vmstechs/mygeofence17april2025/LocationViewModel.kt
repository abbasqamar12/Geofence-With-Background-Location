package com.vmstechs.mygeofence17april2025

import android.location.Location
import androidx.lifecycle.MutableLiveData

object LocationViewModel {
    val locationData = MutableLiveData<Location>()
    val geofenceState = MutableLiveData<String>()

    fun updateLocation(location: Location) {
        locationData.postValue(location)
    }

    fun updateGeofenceState(state: String) {
        geofenceState.postValue(state)
    }
}