package com.example.weather4u.model.local.alertRoom

import android.app.Application
import androidx.room.Room
import com.example.weather4u.model.local.weatherRoom.WeatherDataBase

object AlertInstance {
    fun getInstance(application: Application) : AlertDao {
        return Room.databaseBuilder(application, AlertDataBase::class.java, "AlertDatabase").build().getAlertDao()
    }
}