package com.example.weather4u.model.local.favorite

import android.app.Application
import androidx.room.Room
import com.example.weather4u.model.local.weatherRoom.WeatherDataBase

object FavoriteInstance {
    fun getInstance(application: Application) : FavoriteDao{
        return Room.databaseBuilder(application, FavoriteDataBase::class.java, "FavoriteDatabase").build().getFavoriteDao()
    }
}