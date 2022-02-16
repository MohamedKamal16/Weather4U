package com.example.weather4u.model.local.weatherRoom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherResponse: WeatherResponse?)

    @Query("SELECT * From WeatherResponse")
    fun getAllWeatherData():LiveData<List<WeatherResponse>>

    @Query("SELECT * From WeatherResponse WHERE lon = :lon AND lat= :lat")
    fun getLocationWeather(lat: Double,lon: Double): WeatherResponse

    @Query("DELETE from WeatherResponse WHERE lon = :lon AND lat= :lat")
    suspend fun deleteLocationWeather(lat:Double,lon:Double)

}