package com.example.weather4u.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.weather4u.util.Constant.CURRENT_LATITUDE
import com.example.weather4u.util.Constant.CURRENT_LONGITUDE
import com.example.weather4u.util.Constant.Fav_LATITUDE
import com.example.weather4u.util.Constant.Fav_LONGITUDE
import com.example.weather4u.util.Constant.Last_LATITUDE
import com.example.weather4u.util.Constant.Last_LONGITUDE
import com.example.weather4u.util.Constant.search_LATITUDE
import com.example.weather4u.util.Constant.search_LONGITUDE

object LocationPreferences {

    private fun getSharedPreference(context: Context): SharedPreferences {
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

    fun setSearchLocationPreference(preferences: SharedPreferences,lat:Double,lon:Double){
        val editor =preferences.edit()
        editor.putFloat(search_LATITUDE,lat.toFloat()).apply()
        editor.putFloat(search_LONGITUDE,lon.toFloat()).apply()
    }



    //get value from preference

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
    fun iSCurrentLocation(context: Context):Boolean{
        val sp= PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getBoolean(Constant.CURRENT_LOCATION, true)

    }
    fun getSearchLat(context: Context):Float{
        val sp= PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getFloat(search_LATITUDE, 0.0F)

    }
    fun getSearchLong(context: Context):Float{
        val sp= PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getFloat(search_LONGITUDE, 0.0F)

    }
  }