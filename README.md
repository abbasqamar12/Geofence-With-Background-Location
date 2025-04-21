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

