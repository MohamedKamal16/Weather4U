package com.example.weather4u.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.*
import com.example.weather4u.R
import com.example.weather4u.ui.activity.WeatherActivity
import com.example.weather4u.util.Constant.CURRENT_LOCATION
import com.example.weather4u.util.Constant.LANGUAGE
import com.example.weather4u.util.Constant.Location
import com.example.weather4u.util.Constant.PLACE_API_KEY
import com.example.weather4u.util.Constant.UNIT
import com.example.weather4u.util.LocationPermission
import com.example.weather4u.util.LocationPreferences.setSearchLocationPreference
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

//registerForActivityResult TO USE INSTEAD OF ON ACTIVITY RESULT THAT DEPRECATED IN FRAGMENT
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { activityResult ->
            val data: Intent? = activityResult.data
            when (activityResult.resultCode) {
                AutocompleteActivity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        locationEditTextPreference.text = place.address
                        locationEditTextPreference.isVisible = true
                        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
                        setSearchLocationPreference(pref,place.latLng!!.latitude,place.latLng!!.longitude)
                        pref.edit().putBoolean(CURRENT_LOCATION, false).apply()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    Log.i("place", "RESULT_ERROR ")
                }
                AutocompleteActivity.RESULT_CANCELED -> {
                    Log.i("place", "RESULT_CANCELED")
                }
            }
        }
    )


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        (activity as WeatherActivity).supportActionBar?.show()
        //place autocomplete
        Places.initialize(requireContext(), PLACE_API_KEY)
        val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
            .build(requireContext())
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //preference settings
        locationListPreference = findPreference(Location)!!
        unitListPreference = findPreference(UNIT)!!
        languageListPreference = findPreference(LANGUAGE)!!
        locationEditTextPreference = findPreference("location_address")!!
        locationEditTextPreference.isVisible = !pref.getBoolean(CURRENT_LOCATION, true)

        //setting logic
        locationListPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, value ->
                if (value.toString() == "other") {
                    activityResultLauncher.launch(intent)
                } else {
                    if (LocationPermission.checkPermission(requireContext())) {
                        pref.edit().putBoolean(CURRENT_LOCATION, true).apply()
                        locationEditTextPreference.isVisible = false
                    } else {
                        LocationPermission.requestPermission(requireActivity())
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.no_permission),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                true
            }

        unitListPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, value ->
                Log.i("call", value.toString())
                pref.edit().putString(UNIT, value.toString()).apply()
                true
            }

        languageListPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, value ->
                Log.i("call", value.toString())
                pref.edit().putString(LANGUAGE, value.toString()).apply()
                setLocale(requireContext(), value.toString())
                (activity as WeatherActivity).recreate()
                true
            }
    }

}