package com.example.weather4u.util

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager
import com.example.weather4u.util.Constant.CURRENT_LOCATION
import com.example.weather4u.util.Constant.LANGUAGE
import com.example.weather4u.util.Constant.UNIT
import com.example.weather4u.util.Constant.search_LATITUDE
import com.example.weather4u.util.Constant.search_LONGITUDE
import java.util.*

object SettingFragmentPreference {
     fun loadUnit(context: Context): String {
         val sp= PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(UNIT, "metric").toString()
    }
    fun isNotificationEnabled(context: Context): Boolean {
        val sp= PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getBoolean("NOTIFICATION", false)
    }

    /* fun loadLanguage(context: Context): String {
         val sp= PreferenceManager.getDefaultSharedPreferences(context)
         return sp.getString(LANGUAGE, "en").toString()
    }*/

    fun onAttach(context: Context): Context? {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

  fun loadLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }


    fun setLocale(context: Context, language: String?): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences =PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(LANGUAGE, defaultLanguage)
    }


    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}