package com.example.weather4u.ui.viewModel
/*

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.model.repository.WeatherRepository

class FavoriteFragmentViewModel(application: Application) : AndroidViewModel(application)  {
    private val localData= LocalDatabase.getInstance(application)
    private val localData2=FavoriteInstance.getInstance(application)
    private val localData3= AlertInstance.getInstance(application)
    val repository : WeatherRepository = WeatherRepository(localData,localData2,localData3)

    fun loadFavData(lat: Double, lon: Double, lang :String, unit:String )=repository.loadFavData(lat, lon, lang, unit)

    fun getWeatherFavLiveDat():MutableLiveData<WeatherResponse> =repository.getWeatherFavLiveDat()

    fun insertUpdate(favorite: FavoriteEntity)=repository.insertUpdate(favorite)

    fun getAll() =repository.getAllFavorite()

    fun delete(favorite: FavoriteEntity)=repository.deleteFav(favorite)

    fun getFavoriteInfo(): LiveData<List<FavoriteEntity>> =repository.getFavoriteInfo()






}
*/