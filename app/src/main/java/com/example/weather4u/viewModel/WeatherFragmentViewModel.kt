package com.example.weather4u.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weather4u.model.local.alertRoom.AlertInstance
import com.example.weather4u.model.local.favorite.FavoriteInstance
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.model.local.weatherRoom.LocalDatabase
import com.example.weather4u.model.repository.WeatherRepository


class WeatherFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val localData=LocalDatabase.getInstance(application)
    private val localData2=FavoriteInstance.getInstance(application)
    private val localData3=AlertInstance.getInstance(application)
     val repository :WeatherRepository= WeatherRepository(localData,localData2,localData3)



    fun loadAllData(lat: Double, lon: Double, lang :String, unit:String )=repository.loadAllData(lat, lon, lang, unit)

    fun getWeatherLiveDat():MutableLiveData<WeatherResponse> =repository.getWeatherLiveDat()
    fun getDataOffline(lat: Double, lon: Double, lang :String, unit:String )=repository.getDataOffline(lat, lon, lang, unit)








}