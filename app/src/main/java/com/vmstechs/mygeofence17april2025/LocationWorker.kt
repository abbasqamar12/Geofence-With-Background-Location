package com.vmstechs.mygeofence17april2025

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class LocationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        try {
            Log.d("LocationWorker", "Work started")
            LocationManager.startLocationUpdates(applicationContext)

            delay(10 * 60 * 1000) // run location updates for 10 mins (or remove if you want indefinite)
            LocationManager.stopLocationUpdates()

            return Result.success()
        } catch (e: Exception) {
            Log.e("LocationWorker", "Work failed: ${e.message}")
            return Result.failure()
        }
    }
}