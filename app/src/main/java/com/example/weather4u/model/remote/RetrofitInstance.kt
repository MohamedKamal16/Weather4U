package com.example.weather4u.model.remote

import com.example.weather4u.util.WEATHER_URL

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getWeatherService(): ApiService {
        return Retrofit.Builder().baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
