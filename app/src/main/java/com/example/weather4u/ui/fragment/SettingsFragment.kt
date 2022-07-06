package com.example.weather4u.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.*
import com.example.weather4u.R
import com.example.weather4u.ui.activity.WeatherActivity
import com.example.weather4u.util.Constant.CURRENT_LOCATION
import com.example.weather4u.util.Constant.LANGUAGE
import com.example.weather4u.util.Constant.PLACE_API_KEY
import com.example.weather4u.util.Constant.UNIT
import com.example.weather4u.util.Constant.search_LATITUDE
import com.example.weather4u.util.Constant.search_LONGITUDE
import com.example.weather4u.util.LocationPermission
import com.example.weather4u.util.SettingFragmentPreference.setLocale
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

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
        unitListPreference = findPreference(UNIT)!!
        languageListPreference = findPreference(LANGUAGE)!!
        locationEditTextPreference = findPreference("location_address")!!
        locationEditTextPreference.isVisible = !pref.getBoolean(CURRENT_LOCATION, true)

        //setting logic
        locationListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            if(value.toString() == "other"){
               // startActivityForResult(intent, REQUEST_CODE)
            }else{
                if(LocationPermission.checkPermission(requireContext())){
                    pref.edit().putBoolean(CURRENT_LOCATION, true).apply()
                    locationEditTextPreference.isVisible = false

                }else{
                    LocationPermission.requestPermission(requireActivity())
                    Toast.makeText(requireContext(), getString(R.string.no_permission), Toast.LENGTH_LONG).show()
                }


            }
            true
        }

        unitListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            Log.i("call", value.toString())
            pref.edit().putString(UNIT, value.toString()).apply()
            true
        }

        languageListPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            Log.i("call", value.toString())
            pref.edit().putString(LANGUAGE, value.toString()).apply()
            setLocale(requireContext(),value.toString())
            (activity as WeatherActivity).recreate()
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
                        pref.edit().putFloat(search_LATITUDE, place.latLng!!.latitude.toFloat()).apply()
                        pref.edit().putFloat(search_LONGITUDE, place.latLng!!.longitude.toFloat()).apply()
                        pref.edit().putBoolean(CURRENT_LOCATION, false).apply()
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



}