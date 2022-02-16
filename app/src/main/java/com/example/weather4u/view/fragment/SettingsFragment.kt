package com.example.weather4u.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.*
import com.example.weather4u.R
import com.example.weather4u.util.PLACE_API_KEY
import com.example.weather4u.util.Permission
import com.example.weather4u.util.REQUEST_CODE
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var locationListPreference: ListPreference
    private lateinit var locationEditTextPreference: EditTextPreference
    private lateinit var unitListPreference: ListPreference
    private lateinit var languageListPreference: ListPreference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //place autocomplete
        Places.initialize(requireContext(), PLACE_API_KEY)
        val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(requireContext())
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //preference settings
        locationListPreference = findPreference("location")!!
        unitListPreference = findPreference("unit")!!
        languageListPreference = findPreference("language")!!
        locationEditTextPreference = findPreference("location_address")!!
        locationEditTextPreference.isVisible = !pref.getBoolean("currentLocation", true)

        //setting logic
        locationListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            if(value.toString() == "other"){
                startActivityForResult(intent, REQUEST_CODE)
            }else{
                if(Permission.checkPermission(requireContext())){
                    pref.edit().putBoolean("currentLocation", true).apply()
                    locationEditTextPreference.isVisible = false

                }else{
                    Permission.requestPermission(requireActivity())
                    Toast.makeText(requireContext(), "NO Permission Granted enjoy our other service", Toast.LENGTH_LONG).show()
                }


            }
            true
        }

        unitListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            Log.i("call", value.toString())
            pref.edit().putString("unit", value.toString()).apply()
            true
        }

        languageListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            Log.i("call", value.toString())
            pref.edit().putString("language", value.toString()).apply()
            setLang(value.toString())
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        locationEditTextPreference.text = place.address
                        locationEditTextPreference.isVisible = true

                        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
                        pref.edit().putFloat("lat", place.latLng!!.latitude.toFloat()).apply()
                        pref.edit().putFloat("lon", place.latLng!!.longitude.toFloat()).apply()
                        pref.edit().putBoolean("currentLocation", false).apply()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                    }
                }
                Activity.RESULT_CANCELED -> {

                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setLang(code: String) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources: Resources = requireContext().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

    }
    private lateinit var locationManager : LocationManager

}