package com.example.weather4u.model.local.weatherRoom

import android.app.Application
import androidx.room.Room
//Todo find other way
object LocalDatabase {
    fun getInstance(application: Application) : WeatherDAO{
        return Room.databaseBuilder(application, WeatherDataBase::class.java, "WeatherDatabase").build().getWeatherDao()
    }
}