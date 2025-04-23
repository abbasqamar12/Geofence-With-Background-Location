package com.vmstechs.mygeofence17april2025

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vmstechs.mygeofence17april2025.AppUtils.isWithinShiftHours
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive

import kotlin.coroutines.cancellation.CancellationException

class LocationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("LocationWorker", "Work started")

        try {
            LocationManager.startLocationUpdates(applicationContext)

            while (isWithinShiftHours()) {
                coroutineContext.ensureActive() // Throws CancellationException if cancelled
                delay(60_000) // Periodically check shift status
            }

            Log.d("LocationWorker", "Shift ended or cancelled")
            return Result.success()
        } catch (e: CancellationException) {
            Log.w("LocationWorker", "Work was cancelled")
            return Result.failure()
        } catch (e: Exception) {
            Log.e("LocationWorker", "Work failed", e)
            return Result.failure()
        } finally {
            LocationManager.stopLocationUpdates()
            Log.d("LocationWorker", "Location updates stopped")
        }
    }


}