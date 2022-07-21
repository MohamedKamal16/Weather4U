package com.example.weather4u.repository


import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.model.local.favorite.FavoriteDao
import com.example.weather4u.model.remote.ApiService

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val api: ApiService
) {

    suspend fun getWeather(
        lat: Double?,
        lon: Double?,
        units: String,
        lang: String
    ) = api.getWeatherDetails(lat, lon, units, lang)

    //favorite data base
    suspend fun insert(favorite: FavoriteEntity) = favoriteDao.insert(favorite)
    fun getAllFev() = favoriteDao.getAllFev()
    suspend fun delete(favorite: FavoriteEntity) = favoriteDao.delete(favorite)

}