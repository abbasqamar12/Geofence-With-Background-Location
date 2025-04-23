package com.vmstechs.mygeofence17april2025.permission_utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun hasFineLocation(context: Context): Boolean =
        isGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)

    fun hasBackgroundLocation(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            isGranted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        else true

    fun hasNotificationPermission(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            isGranted(context, Manifest.permission.POST_NOTIFICATIONS)
        else true

    private fun isGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

}