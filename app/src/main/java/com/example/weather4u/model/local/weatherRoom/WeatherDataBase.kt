package com.example.weather4u.model.local.weatherRoom

import androidx.room.Database
import androidx.room.RoomDatabase

//Problem when make all of them in one database search for solve
@Database(
    entities = [WeatherResponse::class],
    version = 1,
    exportSchema = false

)
abstract class WeatherDataBase:RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDAO

}


