package com.example.weather4u.ui.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.example.weather4u.model.dataclass.WeatherResponse
import com.example.weather4u.repository.WeatherRepository
import com.example.weather4u.util.Constant
import com.example.weather4u.util.Constant.NOTIFICATION_WORK
import com.example.weather4u.util.Constant.WORKER_LAT
import com.example.weather4u.util.Constant.WORKER_LON
import com.example.weather4u.util.NetworkCheck.hasInternetConnection
import com.example.weather4u.util.Resource
import com.example.weather4u.util.SettingFragmentPreference
import com.example.weather4u.util.workManger.WeatherWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(
    private val repo: WeatherRepository,
    application: Application
) : BaseViewModel(application) {

    private val workManager = WorkManager.getInstance(application)

    //LiveData
    private val _weatherData: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val weatherLiveData: LiveData<Resource<WeatherResponse>>
        get() = _weatherData


    fun getWeather(lat: Double?, lon: Double?, units: String, lang: String) =
        viewModelScope.launch {
            safeWeatherCall(lat, lon, units, lang)
        }

    private suspend fun safeWeatherCall(lat: Double?, lon: Double?, units: String, lang: String) {
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

    fun createInputDataForWork(lat: Double?, lon: Double?, lang: String, unit: String): Data {
        val builder = Data.Builder()
        lat?.let {
            builder.putDouble(WORKER_LAT, it)
        }
        if (lon != null) {
            builder.putDouble(WORKER_LON, lon)
        }
        builder.putString(Constant.WORKER_Unit, unit)
        builder.putString(Constant.WORKER_Lang, lang)
        return builder.build()
    }


    fun workNotification(context: Context, lat: Double?, lon: Double?, lang: String, unit: String) {
        if (SettingFragmentPreference.isNotificationEnabled(context)) {
            Log.d("workStart", "Notification is on")
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()


            val notificationWork = PeriodicWorkRequest.Builder(
                WeatherWork::class.java,
                3, TimeUnit.HOURS,
            )
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setInputData(createInputDataForWork(lat, lon, lang, unit))
                .setConstraints(constraints)
                .build()


            val instanceWorkManager = WorkManager.getInstance(context)
            instanceWorkManager.enqueueUniquePeriodicWork(
                NOTIFICATION_WORK,
                ExistingPeriodicWorkPolicy.REPLACE, notificationWork
            )

/*another way correct
 val workRequest = OneTimeWorkRequestBuilder<WeatherWork>()
    .setInputData(createInputDataForWork(lat,lon,lang,unit))
    .setConstraints(constraints)
    .build()

workManager.enqueue(workRequest)
*/

        }
    }
}
