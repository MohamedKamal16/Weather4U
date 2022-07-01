package com.example.weather4u.ui.activity

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weather4u.R
import com.example.weather4u.databinding.ActivityWeatherBinding
import com.example.weather4u.util.Constant.PERMISSIONS_REQUEST_LOCATION
import com.example.weather4u.util.Permission
import com.example.weather4u.util.Permission.requestPermission
import com.example.weather4u.util.Preferences
import com.example.weather4u.util.Preferences.createLocationPreference
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class WeatherActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var binding:ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_weather)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_weather,
                R.id.navigation_favorite,
                R.id.AlertFragment,
                R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        createLocationPreference(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermission(this)
        getLastLocation()


    }


    private fun getLastLocation(){
        if(Permission.checkPermission(this)){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        Preferences.setLocationPreference(applicationContext, location.latitude,location.longitude)
                    }
                }

        }else{
            requestPermission(this)
        }
    }

    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //check using Permission.checkPermission(context)
        if (Permission.checkPermission(this)){
        Looper.myLooper()?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback, it)
        }}
    }
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            //last location on prefernce
            Preferences.setLastLocationPreference(applicationContext,lastLocation.latitude,lastLocation.longitude)
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        }
    }



}





