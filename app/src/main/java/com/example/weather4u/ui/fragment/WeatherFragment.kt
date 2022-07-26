package com.example.weather4u.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather4u.databinding.FragmentWeatherBinding
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.model.dataclass.WeatherResponse
import com.example.weather4u.ui.activity.WeatherActivity
import com.example.weather4u.ui.adabter.currentWeather.CurrentAdapter
import com.example.weather4u.ui.adabter.currentWeather.DailyAdapter
import com.example.weather4u.ui.adabter.currentWeather.HourlyAdapter
import com.example.weather4u.ui.viewModel.WeatherFragmentViewModel
import com.example.weather4u.util.Animation.changeWeather
import com.example.weather4u.util.LocationPreferences.getCurrentLat
import com.example.weather4u.util.LocationPreferences.getCurrentLong
import com.example.weather4u.util.LocationPreferences.getSearchLat
import com.example.weather4u.util.LocationPreferences.getSearchLong
import com.example.weather4u.util.LocationPreferences.iSCurrentLocation
import com.example.weather4u.util.Resource
import com.example.weather4u.util.SettingFragmentPreference.loadLanguage
import com.example.weather4u.util.SettingFragmentPreference.loadUnit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var dailyListAdapter: DailyAdapter
    private lateinit var hourlyListAdapter: HourlyAdapter
    private lateinit var currentAdapter: CurrentAdapter
    private val viewModel: WeatherFragmentViewModel by viewModels()

    private var dailyList = mutableListOf<DailyItem?>()
    private var hourlyList = mutableListOf<HourlyItem?>()
    private var weatherItem: WeatherResponse? = null
    private var unit = String()

    private var lat = 0.0
    private var lon = 0.0
    private var language = String()
    private var isCurrentLocation = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as WeatherActivity).supportActionBar?.hide()
        getData()
        init()
        observe()

    }

    private fun observe() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let { weatherResponse ->
                        dailyList = weatherResponse?.daily!!.toMutableList()
                        hourlyList = weatherResponse.hourly!!.toMutableList()
                        weatherItem = weatherResponse

                        dailyListAdapter.updateDailyList(dailyList)
                        hourlyListAdapter.updateHoursList(hourlyList)
                        currentAdapter.updateCurrent(weatherItem!!)
                        // animation work but after add xml but no need
                        //   changeWeather(binding.WeatherAnimation,weatherItem?.current?.weather?.get(0)?.main)
                        Log.d("Weather", "observe:${weatherItem?.current?.weather?.get(0)?.main} ")
                    }
                }
                is Resource.Error -> {
                    showProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured:$message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun getData() {
        unit = loadUnit(requireContext())
        language = loadLanguage(requireContext()).toString()
        isCurrentLocation = iSCurrentLocation(requireContext())
        if (isCurrentLocation) {
            lat = getCurrentLat(requireContext()).toDouble()
            lon = getCurrentLong(requireContext()).toDouble()
        } else {
            lat = getSearchLat(requireContext()).toDouble()
            lon = getSearchLong(requireContext()).toDouble()
        }
        viewModel.getWeather(lat, lon, unit, language)
        viewModel.workNotification(requireContext(), lat, lon, language, unit)
    }


    private fun init() {
        dailyListAdapter = DailyAdapter(dailyList)
        hourlyListAdapter = HourlyAdapter(hourlyList)
        currentAdapter = CurrentAdapter(weatherItem, requireContext(), unit)
        with(binding) {
            recyclerViewDaily.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = dailyListAdapter
            }

            recyclerViewHourly.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = hourlyListAdapter
            }
            rvCurrent.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = currentAdapter
            }
            binding.pbLoading.visibility = View.VISIBLE
        }
    }
}
