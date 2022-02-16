package com.example.weather4u.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object Preferences {

    //create preference for location
     fun createLocationPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_CURRENT_LOCATION, AppCompatActivity.MODE_PRIVATE)
    }


    //set value on preference
    fun setLocationPreference(context:Context,lat:Double,lon:Double){
        val editor = createLocationPreference(context).edit()
        editor.putFloat(CURRENT_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(CURRENT_LONGITUDE,lon.toFloat()).apply()
    }
    fun setLastLocationPreference(context:Context,lat:Double,lon:Double){
        val editor = createLocationPreference(context).edit()
        editor.putFloat(Last_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(Last_LONGITUDE,lon.toFloat()).apply()
    }

    fun setFavoriteLocationPreference(context:Context,lat:Double,lon:Double){
        val editor = createLocationPreference(context).edit()
        editor.putFloat(Fav_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(Fav_LONGITUDE,lon.toFloat()).apply()
    }

    //get value from preference
  /*  fun getFavLat(context:Context):Double{
        val pref=createLocationPreference(context)
        val lastLat=  pref.getFloat(Fav_LATITUDE,0.0F)
        return lastLat.toDouble()
    }

    fun getFavLong(context:Context):Double{
        val pref=createLocationPreference(context)
        val lastLon=  pref.getFloat(Fav_LONGITUDE,0.0F)
        return lastLon.toDouble()
    }

   fun getLastLat(context:Context):Double{
        val pref=createLocationPreference(context)
        val lastLat=  pref.getFloat(Last_LATITUDE,0.0F)
        return lastLat.toDouble()
    }

    fun getLastLong(context:Context):Double{
        val pref=createLocationPreference(context)
        val lastLon=  pref.getFloat(Last_LONGITUDE,0.0F)
        return lastLon.toDouble()
    }*/
    fun getCurrentLat(context:Context):Double{
        val pref=createLocationPreference(context)
        val currentLat=  pref.getFloat(CURRENT_LATITUDE,0.0F)
        return currentLat.toDouble()
    }
    fun getCurrentlong(context:Context):Double{
        val pref=createLocationPreference(context)
        val currentLon =  pref.getFloat(CURRENT_LONGITUDE,0.0F)
        return currentLon.toDouble()
    }

  }