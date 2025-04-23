package com.vmstechs.mygeofence17april2025.permission_utils

sealed class PermissionState {
    object Granted : PermissionState()
    data class Denied(val shouldShowRationale: Boolean) : PermissionState()
}