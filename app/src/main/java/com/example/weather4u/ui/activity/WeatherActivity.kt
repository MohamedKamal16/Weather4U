package com.example.weather4u.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weather4u.R
import com.example.weather4u.databinding.ActivityWeatherBinding
import com.example.weather4u.util.Constant.PERMISSIONS_REQUEST_LOCATION
import com.example.weather4u.util.LocationPermission.checkPermission
import com.example.weather4u.util.LocationPermission.isLocationEnabled
import com.example.weather4u.util.LocationPermission.requestPermission
import com.example.weather4u.util.LocationPreferences
import com.example.weather4u.util.LocationPreferences.setLastLocationPreference
import com.example.weather4u.util.SettingFragmentPreference.onAttach
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    private lateinit var binding: ActivityWeatherBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.navView
        navBind()
        getCurrentLocation()

    }

    private fun navBind() {
        navController = findNavController(R.id.newsNavHostFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.AlertFragment) {
                //    binding.navView.visibility = View.GONE
                Toast.makeText(this, "this", Toast.LENGTH_SHORT).show()
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun getCurrentLocation() {
        if (isLocationEnabled(this)) {
            getLastLocation()
        } else {
            Snackbar.make(binding.container, getString(R.string.open_gps), Snackbar.LENGTH_LONG)
                .setAction(getString(
                    R.string.open
                ),
                    View.OnClickListener {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }).show()
        }
    }

    private fun getLastLocation() {
        if (checkPermission(this)) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location == null) {
                    newLocationData()
                } else {
                    Log.d("Debug:", "Your Location:" + location.latitude + "/" + location.longitude)
                    LocationPreferences.setLocationPreference(
                        this,
                        location.latitude,
                        location.longitude
                    )
                }
            }

        } else {
            requestPermission(this)
        }
    }

    private fun newLocationData() {
        if (checkPermission(this)) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 0
                fastestInterval = 0
                numUpdates = 1
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

                Looper.myLooper()?.let {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, it
                )
            }

        }
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            //last location on preference
            setLastLocationPreference(this@WeatherActivity, lastLocation.latitude, lastLocation.longitude)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GRANTED", Toast.LENGTH_LONG).show()
                recreate()

            } else {
                Toast.makeText(
                    this,
                    "You SHOULD ACCEPT PERMISSION TO ENJOY WITH OUR SERVICE",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { onAttach(it) })
    }
}





