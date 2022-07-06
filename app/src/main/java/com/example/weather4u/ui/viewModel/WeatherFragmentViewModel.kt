package com.example.weather4u.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.repository.WeatherRepository
import com.example.weather4u.util.NetworkCheck.hasInternetConnection
import com.example.weather4u.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel@Inject constructor(
    private val repo:WeatherRepository,
    application: Application) :BaseViewModel(application) {

    //LiveData
    private val _weatherData: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val weatherLiveData:LiveData<Resource<WeatherResponse>>
         get() =_weatherData


    fun getWeather(lat: Double?, lon: Double?, units: String, lang: String) = viewModelScope.launch {
        safeWeatherCall(lat, lon, units, lang)
    }

    private suspend fun safeWeatherCall( lat: Double?, lon: Double?, units: String, lang: String) {
        _weatherData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                val response = repo.getWeather(lat, lon, units, lang)
                //post data in mutable live data
                _weatherData.postValue(handleWeatherResponse(response))
            } else {
                _weatherData.postValue(Resource.Error("NO INTERNET CONNECTION"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _weatherData.postValue(Resource.Error("NETWORK FAILURE"))
                else -> _weatherData.postValue(Resource.Error("CONVERSION ERROR"))
            }

        }
    }

    private fun handleWeatherResponse(response: Response<WeatherResponse>): Resource<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }







}