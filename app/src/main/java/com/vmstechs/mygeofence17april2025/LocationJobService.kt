package com.vmstechs.mygeofence17april2025

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager

class LocationJobService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Handler(Looper.getMainLooper()).post {
            when (params?.jobId) {
                JobSchedulerUtil.JOB_ID_START -> {
                    /*val intent = Intent(this, LocationForegroundService::class.java)
                    ContextCompat.startForegroundService(this, intent)*/

                    Log.d("JobService", "Scheduling LocationWorker")
                    val request = OneTimeWorkRequestBuilder<LocationWorker>()
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .build()

                    WorkManager.getInstance(this).enqueue(request)
                }
                JobSchedulerUtil.JOB_ID_STOP -> {
                   /* val stopIntent = Intent(this, LocationForegroundService::class.java).apply {
                        action = LocationForegroundService.ACTION_STOP
                    }
                    ContextCompat.startForegroundService(this, stopIntent)*/

                    Log.d("JobService", "Stopping Location Updates")
                    LocationManager.stopLocationUpdates()
                }
            }
        }
        jobFinished(params, false)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean = false
}