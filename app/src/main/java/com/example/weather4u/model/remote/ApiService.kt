package com.example.weather4u.model.remote

import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall?")
    suspend fun getWeatherDetails(@Query("lat") lat: Double?,
                                  @Query("lon") lon: Double?,
                                  @Query("exclude") exclude:String,
                                  @Query("units") units:String,
                                  @Query("lang") lang: String,
                                  @Query("appid") appid:String
    ): Response<WeatherResponse>

}
