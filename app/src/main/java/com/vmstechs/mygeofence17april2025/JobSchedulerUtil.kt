package com.vmstechs.mygeofence17april2025

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import java.util.Calendar

object JobSchedulerUtil {
    const val JOB_ID_START = 1001
    const val JOB_ID_STOP = 1002

    fun scheduleJobs(context: Context) {
        scheduleJob(context, JOB_ID_START, 14, 34) //Start service at 9:00 AM
        scheduleJob(context, JOB_ID_STOP, 14, 38) //Stop service at 7:00 PM
    }

    private fun scheduleJob(context: Context, jobId: Int, hour: Int, minute: Int) {
        val component = ComponentName(context, LocationJobService::class.java)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val builder = JobInfo.Builder(jobId, component)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .setMinimumLatency(calendar.timeInMillis - System.currentTimeMillis())
            .setOverrideDeadline(calendar.timeInMillis - System.currentTimeMillis() + 5000)

        (context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(builder.build())
    }
}