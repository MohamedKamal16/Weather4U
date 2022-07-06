package com.example.weather4u.util

import android.content.Context
import android.content.SharedPreferences
import com.example.weather4u.util.Constant.CURRENT_LATITUDE
import com.example.weather4u.util.Constant.CURRENT_LONGITUDE
import com.example.weather4u.util.Constant.Fav_LATITUDE
import com.example.weather4u.util.Constant.Fav_LONGITUDE
import com.example.weather4u.util.Constant.Last_LATITUDE
import com.example.weather4u.util.Constant.Last_LONGITUDE

object LocationPreferences {

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    //set value on preference
    fun setLocationPreference(context: Context,lat:Double,lon:Double){
        val editor = getSharedPreference(context).edit()
        editor.putFloat(CURRENT_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(CURRENT_LONGITUDE,lon.toFloat()).apply()
    }
    fun setLastLocationPreference(context: Context,lat:Double,lon:Double){
        val editor = getSharedPreference(context).edit()
        editor.putFloat(Last_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(Last_LONGITUDE,lon.toFloat()).apply()
    }

    fun setFavoriteLocationPreference(context: Context,lat:Double,lon:Double){
        val editor = getSharedPreference(context).edit()
        editor.putFloat(Fav_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(Fav_LONGITUDE,lon.toFloat()).apply()
    }

    //get value from preference
    fun getFavLat(context: Context): Float {
        return getSharedPreference(context).getFloat(Fav_LATITUDE, 0.0F)
    }

    fun getFavLong(context: Context): Float {
        return getSharedPreference(context).getFloat(Fav_LONGITUDE, 0.0F)
    }

    fun getLastLat(context: Context): Float {
        return getSharedPreference(context).getFloat(Last_LATITUDE, 0.0F)
    }

    fun getLastLong(context: Context): Float {
        return getSharedPreference(context).getFloat(Last_LONGITUDE, 0.0F)
    }
    fun getCurrentLat(context: Context): Float {
        return getSharedPreference(context).getFloat(CURRENT_LATITUDE, getLastLat(context))
    }
    fun getCurrentLong(context: Context): Float {
        return getSharedPreference(context).getFloat(CURRENT_LONGITUDE, getLastLong(context))
    }

  }