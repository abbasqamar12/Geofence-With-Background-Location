package com.vmstechs.mygeofence17april2025

import java.util.Calendar

object AppUtils {
     fun isWithinShiftHours(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Assuming shift is Monday to Friday, 9AM to 7PM
        val isWeekday = dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY
        return isWeekday && hour in 9..18 // up to 6:59 PM
    }

}