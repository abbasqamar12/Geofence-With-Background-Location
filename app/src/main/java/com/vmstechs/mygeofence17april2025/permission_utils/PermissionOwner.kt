package com.vmstechs.mygeofence17april2025.permission_utils

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity

interface PermissionOwner {
    val hostContext: Context
    val hostActivity: FragmentActivity
    fun registerPermissionRequest(
        request: (ActivityResultLauncher<Array<String>>, ActivityResultLauncher<String>, ActivityResultLauncher<String>) -> Unit
    )
}