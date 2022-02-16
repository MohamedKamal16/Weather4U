package com.example.weather4u.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather4u.model.local.alertRoom.AlertDao
import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.model.local.favorite.FavoriteDao
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.model.local.weatherRoom.WeatherDAO
import com.example.weather4u.model.remote.RetrofitInstance
import com.example.weather4u.util.API_KEY
import com.example.weather4u.util.MINUTELY
import kotlinx.coroutines.*

class WeatherRepository(private val weatherDAO: WeatherDAO,private val favoriteDao: FavoriteDao,private val alertDao: AlertDao) {
    //live Data
    private val weatherLiveData = MutableLiveData<WeatherResponse>()
    private val weatherFavLiveData = MutableLiveData<WeatherResponse>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private val favoriteData = MutableLiveData<List<FavoriteEntity>>()
    private val error = MutableLiveData<String>()

    private val alertInfo = MutableLiveData<List<AlertEntity>>()



    //retrofit call
    private suspend fun getWeatherDetails(lat: Double?, lon: Double?, exclude:String, units:String, lang: String, appid:String)
            =RetrofitInstance.getWeatherService().getWeatherDetails(lat, lon, exclude, units, lang, appid)

    //weather Database
    suspend fun insert(weatherResponse: WeatherResponse?)=weatherDAO.insert(weatherResponse)
    fun getAllWeatherData()=weatherDAO.getAllWeatherData()
    suspend  fun deleteLocationWeather(lat:Double, lon:Double)=weatherDAO.deleteLocationWeather(lat, lon)
    fun getLocationWeather(lat: Double,lon: Double)=weatherDAO.getLocationWeather(lat, lon)

    //favorite data base
    suspend fun insert(favorite: FavoriteEntity)=favoriteDao.insert(favorite)
    private fun getAllFev(): List<FavoriteEntity> =favoriteDao.getAllFev()
    suspend fun delete(favorite: FavoriteEntity)=favoriteDao.delete(favorite)

    //Alert DataBase
    suspend fun insert(alert: AlertEntity)=alertDao.insert(alert)
    fun getAllAlerts(): List<AlertEntity> = alertDao.getAll()
    fun getSome(alertTime: String, enabled: Boolean): List<AlertEntity> = alertDao.getSome(alertTime, enabled)
    suspend fun delete(alert: AlertEntity)=alertDao.delete(alert)


    //method to handle data
    fun loadAllData(lat: Double, lon: Double, lang :String, unit:String ){
        val exceptionHandlerException = CoroutineExceptionHandler { _, t:Throwable ->
            loadingLiveData.postValue(false)
        }

        CoroutineScope(Dispatchers.IO+exceptionHandlerException).launch {
            val response =getWeatherDetails(lat, lon, MINUTELY, unit, lang, API_KEY)
            withContext(Dispatchers.Main) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    weatherLiveData.postValue(response.body())
                  insert(response.body())

                }
            }
        }
    }
    fun getDataOffline(lat: Double, lon: Double, lang :String, unit:String ){
            CoroutineScope(Dispatchers.IO ).launch {
                if (lat!=null&&lon!=null){
                    val data =getLocationWeather(lat,lon)
                    withContext(Dispatchers.Main) {
                    weatherLiveData.postValue(data)
                }
            }
        }

    }
    fun getWeatherLiveDat():MutableLiveData<WeatherResponse> {
        return weatherLiveData
    }


    //my method to favorite
    fun loadFavData(lat: Double, lon: Double, lang :String, unit:String ){
        val exceptionHandlerException = CoroutineExceptionHandler { _, t:Throwable ->
            loadingLiveData.postValue(false)
        }

        CoroutineScope(Dispatchers.IO+exceptionHandlerException).launch {
            val response =getWeatherDetails(lat, lon, MINUTELY, unit, lang, API_KEY)
            withContext(Dispatchers.Main) {
                loadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    weatherFavLiveData.postValue(response.body())
                }
            }
        }
    }

    fun insertUpdate(favorite: FavoriteEntity){
        val coroutineExceptionHandler = CoroutineExceptionHandler{  _, th ->
            error.postValue(th.localizedMessage)
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
             insert(favorite)
        }
    }

    fun getAllFavorite() {
        val coroutineExceptionHandler = CoroutineExceptionHandler{  _, t ->
            error.postValue(t.message)
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val data = getAllFev()
            withContext(Dispatchers.Main) {
                favoriteData.postValue(data)
            }
        }
    }

    fun deleteFav(favorite: FavoriteEntity){
        val coroutineExceptionHandler = CoroutineExceptionHandler{  _, th ->
            error.postValue(th.localizedMessage)
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
         delete(favorite)
        }
    }

    fun getFavoriteInfo(): LiveData<List<FavoriteEntity>> {
        return favoriteData
    }

    fun getWeatherFavLiveDat():MutableLiveData<WeatherResponse> {
        return weatherFavLiveData
    }

    //Alert Method
    fun insertAlert(alert: AlertEntity){
        val coroutineExceptionHandler = CoroutineExceptionHandler{  _, th ->
            error.postValue(th.localizedMessage)
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            alertDao.insert(alert)
        }
    }

    fun getAllAlert() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = alertDao.getAll()
            withContext(Dispatchers.Main) {
                alertInfo.postValue(data)
            }
        }
    }

    fun deleteAlert(alert: AlertEntity){
        val coroutineExceptionHandler = CoroutineExceptionHandler{  _, th ->
            error.postValue(th.localizedMessage)
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            alertDao.delete(alert)
        }
    }

    fun getAlertInfo(): LiveData<List<AlertEntity>> {
        return alertInfo
    }



}