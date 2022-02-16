package com.example.weather4u.view.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weather4u.R
import com.example.weather4u.databinding.FragmentWeatherBinding
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.util.*
import com.example.weather4u.view.adabter.currentWeather.DailyAdapter
import com.example.weather4u.view.adabter.currentWeather.HourlyAdapter
import com.example.weather4u.viewModel.WeatherFragmentViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class WeatherFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(WeatherFragmentViewModel::class.java)
        dailyListAdapter = DailyAdapter(arrayListOf(),viewModel)
        hourlyListAdapter = HourlyAdapter(arrayListOf(),viewModel)
        val root: View = binding.root
        init()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observe change in data to change it
        viewModel.getWeatherLiveDat() .observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                data.let { currentDetail(it) }
                data.hourly?.let { it1 -> updateHourlyListUI(it1 as List<HourlyItem>) }
                data.daily?.let { it1 -> updateDailyListUI(it1 as List<DailyItem>) }

            }
        })
        viewModel.repository.loadingLiveData.observe(viewLifecycleOwner) { showLoading(it) }


    }

    override fun onResume() {
        super.onResume()
        //get setting data from Preference
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        lat = pref.getFloat("lat", 0.0F).toDouble()
        lon = pref.getFloat("lon", 0.0F).toDouble()
        unit = pref.getString("unit", "metric").toString()
        language = pref.getString("language", "en").toString()
        val currentLocation = pref.getBoolean("currentLocation", true)
        // Handle calling



            if (currentLocation) {
                if (Network.isOnline(requireContext())) {
                    if (Permission.checkPermission(requireContext())) {
                    if (isLocationEnabled(requireContext())) {

                            viewModel.loadAllData(
                                Preferences.getCurrentLat(requireContext()),
                                Preferences.getCurrentlong(requireContext()),
                                language,
                                unit
                            )
                        } else {
                        Toast.makeText(requireContext(), "Check Gps is Active ",
                            Toast.LENGTH_LONG).show()
                            viewModel.loadAllData(lat, lon, language, unit) }
                    }
                    else {
                        viewModel.loadAllData(lat, lon, language, unit) }
                }
                else { Toast.makeText(requireContext(), "No Network",
                    Toast.LENGTH_LONG).show()
                    viewModel.getDataOffline(lat, lon, language, unit) }
            }
            //show API search location
            else { viewModel.loadAllData(lat, lon, language, unit) }
    }

    //To show and hide progress bar
    private fun showLoading(flag: Boolean) {
        if(flag){
            loading.visibility = View.VISIBLE
        }else{
            loading.visibility = View.GONE
        }
    }

    //refresh recycler Ui
    private fun updateDailyListUI(it: List<DailyItem>) {
        dailyListAdapter.updateHoursList(it)
    }
    private fun updateHourlyListUI(it: List<HourlyItem>) {
        hourlyListAdapter.updateHoursList(it)
    }
    //Current Weather Data ui
    private fun currentDetail(weatherResponse: WeatherResponse) {
        CoroutineScope(Dispatchers.Main).launch{

            city.text = weatherResponse.timezone?.substringAfter("/")
            date_time.text =Time.currentTime(weatherResponse.current?.dt?.toLong())
            tv_desc.text = weatherResponse.current?.weather?.get(0)?.description
            windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString()
            clouds.text = getText(R.string.clouds).toString()+ weatherResponse.current?.clouds.toString() + "%"
            data_humidity.text=weatherResponse.current?.humidity.toString()
            data_visibility.text=weatherResponse.current?.visibility.toString()
            data_pressure.text=weatherResponse.current?.pressure.toString()
            feels_like.text= weatherResponse.current?.feelsLike.toString()

            when (unit) {
                "imperial" ->
                {
                    temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.fahrenheit)}"
                    windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString() +
                            getText(R.string.mileHr)
                }
                "metric" ->{
                    temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.celsius)}"
                    windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString() +
                            getText(R.string.meterSec)
                }
                else -> temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.kelvin)}"
            }
        }

        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(this@WeatherFragment).
                  load(weatherResponse.current?.weather?.get(0)?.icon?.let { Icon.getIcon(it) })
                .centerCrop()
                . into(binding.imageView)
        }
    }

    //permission granted result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        }


    }
    //check gps condition
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }



    private fun init(){
        binding.recyclerViewDaily .layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewDaily.adapter=dailyListAdapter
        binding.recyclerViewHourly .layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewHourly.adapter=hourlyListAdapter
        loading=binding.pbLoading
    }


    private lateinit var loading: ProgressBar
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherFragmentViewModel
    private lateinit var dailyListAdapter:DailyAdapter
    private lateinit var hourlyListAdapter :HourlyAdapter
    private var lat = 0.0
    private var lon = 0.0
    private var unit = String()
    private var language = String()
}

