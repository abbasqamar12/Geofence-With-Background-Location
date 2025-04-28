package com.vmstechs.mygeofence17april2025

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.vmstechs.mygeofence17april2025.permission_utils.PermissionHandler
import com.vmstechs.mygeofence17april2025.permission_utils.PermissionOwner
import com.vmstechs.mygeofence17april2025.permission_utils.PermissionState

class MainActivity : AppCompatActivity(), PermissionOwner {
    private lateinit var permissionHandler: PermissionHandler
    override val hostContext: Context
        get() = this

    override val hostActivity: FragmentActivity
        get() = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        permissionHandler = PermissionHandler(this)

        registerPermissionRequest { locLauncher, bgLauncher, notifLauncher ->
            locationPermissionLauncher = locLauncher
            backgroundPermissionLauncher = bgLauncher
            notificationPermissionLauncher = notifLauncher
        }

        observePermissions()

        // Start requesting permissions
        permissionHandler.requestLocationPermission()

        //JobSchedulerUtil.scheduleJobs(this)

        LocationViewModel.locationData.observe(this) {
            // Update your UI
            Log.d("LocationViewModel", "Location updated: ${it.latitude}, ${it.longitude}")
        }

        LocationViewModel.geofenceState.observe(this) {
            // Update your UI
            Log.d("LocationViewModel", "Geofence state updated: $it")
        }
    }

    private fun observePermissions() {
        permissionHandler.locationPermissionState.observe(this, Observer { state ->
            if (state is PermissionState.Granted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    permissionHandler.requestBackgroundLocationPermission()
                } else {
                    permissionHandler.requestNotificationPermission()
                }
            } else if (state is PermissionState.Denied && state.shouldShowRationale) {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            }
        })

        permissionHandler.backgroundPermissionState.observe(this, Observer { state ->
            if (state is PermissionState.Granted) {
                permissionHandler.requestNotificationPermission()
            } else if (state is PermissionState.Denied && state.shouldShowRationale) {
                Toast.makeText(this, "Background permission is required", Toast.LENGTH_SHORT).show()
            }
        })
        permissionHandler.notificationPermissionState.observe(this, Observer { state ->
            if (state is PermissionState.Granted) {
                // All permissions granted
                //startLocationTracking
                Log.d("MainActivity", "All permissions granted")

                JobSchedulerUtil.scheduleJobs(this)


            } else if (state is PermissionState.Denied && state.shouldShowRationale) {
                Toast.makeText(this, "Notification permission is required", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    // Permission request launcher holders
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var backgroundPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    override fun registerPermissionRequest(request: (ActivityResultLauncher<Array<String>>, ActivityResultLauncher<String>, ActivityResultLauncher<String>) -> Unit) {
        val locLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result[android.Manifest.permission.ACCESS_FINE_LOCATION] == true
            permissionHandler.handlePermissionResult(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                granted
            )
        }

        val bgLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            permissionHandler.handlePermissionResult(
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                granted
            )
        }

        val notifLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            permissionHandler.handlePermissionResult(
                android.Manifest.permission.POST_NOTIFICATIONS,
                granted
            )
        }

        request(locLauncher, bgLauncher, notifLauncher)
    }

}