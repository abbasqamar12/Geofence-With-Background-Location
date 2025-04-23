package com.vmstechs.mygeofence17april2025.permission_utils

import android.Manifest
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PermissionHandler(private val owner: PermissionOwner) {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var backgroundPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    private val _locationPermissionState = MutableLiveData<PermissionState>()
    val locationPermissionState: LiveData<PermissionState> get() = _locationPermissionState

    private val _backgroundPermissionState = MutableLiveData<PermissionState>()
    val backgroundPermissionState: LiveData<PermissionState> get() = _backgroundPermissionState

    private val _notificationPermissionState = MutableLiveData<PermissionState>()
    val notificationPermissionState: LiveData<PermissionState> get() = _notificationPermissionState

    init {
        owner.registerPermissionRequest { locLauncher, bgLauncher, notifLauncher ->
            locationPermissionLauncher = locLauncher
            backgroundPermissionLauncher = bgLauncher
            notificationPermissionLauncher = notifLauncher
        }
    }

    fun requestLocationPermission() {
        if (PermissionUtils.hasFineLocation(owner.context)) {
            _locationPermissionState.value = PermissionState.Granted
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ||
            PermissionUtils.hasBackgroundLocation(owner.context)
        ) {
            _backgroundPermissionState.value = PermissionState.Granted
        } else {
            backgroundPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            PermissionUtils.hasNotificationPermission(owner.context)
        ) {
            _notificationPermissionState.value = PermissionState.Granted
        } else {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun handlePermissionResult(permission: String, granted: Boolean) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                _locationPermissionState.value = if (granted)
                    PermissionState.Granted else PermissionState.Denied(true)
            }
            Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                _backgroundPermissionState.value = if (granted)
                    PermissionState.Granted else PermissionState.Denied(true)
            }
            Manifest.permission.POST_NOTIFICATIONS -> {
                _notificationPermissionState.value = if (granted)
                    PermissionState.Granted else PermissionState.Denied(true)
            }
        }
    }

}