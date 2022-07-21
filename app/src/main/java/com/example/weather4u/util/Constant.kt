package com.example.weather4u.util

import android.content.Context
import android.content.SharedPreferences

object Constant{
    //Room
    const val ROOM_DATABASE_NAME = "ROOM_DATABASE_NAME"
    //API
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "2fa69c0eba706e881f5af9aabdf7698e"
    const val MINUTELY = "minutely"
    const val PLACE_API_KEY = "AIzaSyDYU0FPpbhkdhQSj3n3vJ1SghZX_DrxcYk"
    //SharedPreferences
    const val SHARED_PREFERENCES_NAME="pref"
    const val LANGUAGE="language"
    const val UNIT="unit"
    const val Location="location"
    //Location
    const val PERMISSIONS_REQUEST_LOCATION: Int = 99
    const val CURRENT_LATITUDE="currentLat"
    const val CURRENT_LONGITUDE="currentLon"
    const val Last_LATITUDE="lastLat"
    const val Last_LONGITUDE="lastLon"
    const val Fav_LATITUDE="favLat"
    const val Fav_LONGITUDE="favLon"
    const val search_LATITUDE="sLat"
    const val search_LONGITUDE="sLon"
    const val CURRENT_LOCATION="currentLocation"
    //Notification
    const val NOTIFICATION_CHANNEL_NAME="WEATHER_CHANNEL"
    const val NOTIFICATION_CHANNEL_DESCRIPTION="WEATHER_DESCRIPTION"
    const val CHANNEL_ID = "WEATHER_ID"
    const val NOTIFICATION_ID = 555
    const val NOTIFICATION_TITLE = "Weather"
    //worker Key
    const val WORKER_LAT = "WORKER_LAT"
    const val WORKER_LON = "WORKER_LON"
    const val WORKER_Unit = "WORKER_Unit"
    const val WORKER_Lang = "WORKER_Lang"
    const val NOTIFICATION_WORK = "weather_notification_work"

    const val SplashTimeOut = 4000

}


