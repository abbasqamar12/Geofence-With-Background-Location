# 🛰️ Geofence & Location Tracking App (Scheduled Shift Hours)

This is an Android app for **location tracking and geofencing** that only works during scheduled shift hours (e.g., 9:00 AM to 7:00 PM). The app uses Android's `JobScheduler` to start and stop location updates and geofence monitoring automatically, ensuring battery efficiency and compliance with Android 12+ restrictions.

---

## 🚀 Features

- ⏰ **Shift-Based Tracking**  
  Location and geofence tracking starts at 9:00 AM and stops at 7:00 PM (configurable).

- 🧭 **Fused Location Provider**  
  Accurate and battery-optimized location updates using Google's `FusedLocationProviderClient`.

- 📍 **Geofencing Support**  
  Supports geofence entry and exit detection with background handling.

- 🔒 **Android 12+ Compliant**  
  No dependency injection. Foreground service restrictions handled via `JobScheduler`.

- ⚙️ **Auto Restart on Boot**  
  Jobs are re-scheduled automatically when the device reboots.

---

## 📂 Project Structure

MyGeofenceApp/ 
├── LocationJobService.kt # JobService for managing location tracking 
├── GeofenceReceiver.kt # Receives geofence events (ENTER/EXIT) 
├── LocationServiceManager.kt # Handles start/stop of location updates 
├── JobSchedulerUtil.kt # Schedules jobs at shift start/stop times 
├── LocationUtils.kt # Utility for permission and location functions 
├── MainActivity.kt # UI to monitor and manage geofencing status 
└── AndroidManifest.xml # App component declarations


---

## 🔑 Permissions Required

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

Ensure runtime permission requests are handled for Android 10+ and background location.

🛠️ How It Works
On App Start or Boot Complete:

JobSchedulerUtil.scheduleJobs() sets up daily start and stop jobs.

At Shift Start Time (e.g., 9:00 AM):

LocationJobService starts

Location updates and geofencing are activated

At Shift End Time (e.g., 7:00 PM):

Location updates and geofencing are stopped

GeofenceReceiver:

Handles background entry/exit events and logs them

✅ To Run This Project
Clone this repository

Open in Android Studio

Run on a real device (emulator geofencing support is limited)

Grant location and background location permissions

Monitor logs with Logcat for geofence transition events

🐞 Troubleshooting
Geofence not triggering?

Ensure device location is ON

Grant all permissions including background location

Use real location or mock tools for testing

Service not starting on time?

Verify JobScheduler is correctly scheduling jobs

Check device battery optimizations

