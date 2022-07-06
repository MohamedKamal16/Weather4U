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
    //SharedPreferences
    const val SHARED_PREFERENCES_NAME="pref"
    const val LANGUAGE="language"
    const val UNIT="unit"


    //Location
    const val PERMISSIONS_REQUEST_LOCATION: Int = 99
   // const val Location_update_Interval=5000L
   // const val   FASTET_Location_Interval=2000L
    const val CURRENT_LATITUDE="currentLat"
    const val CURRENT_LONGITUDE="currentLon"
    const val Last_LATITUDE="lastLat"
    const val Last_LONGITUDE="lastLon"
    const val Fav_LATITUDE="favLat"
    const val Fav_LONGITUDE="favLon"
    const val search_LATITUDE="sLat"
    const val search_LONGITUDE="sLon"
    const val CURRENT_LOCATION="currentLocation"



    const val PLACE_API_KEY = "AIzaSyDYU0FPpbhkdhQSj3n3vJ1SghZX_DrxcYk"
   // const val REQUEST_CODE: Int = 100



    const val SplashTimeOut = 4000

}


