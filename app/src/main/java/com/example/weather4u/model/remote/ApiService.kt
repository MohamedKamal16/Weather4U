package com.example.weather4u.model.remote

import com.example.weather4u.model.dataclass.WeatherResponse
import com.example.weather4u.util.Constant.API_KEY
import com.example.weather4u.util.Constant.MINUTELY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall?")
    suspend fun getWeatherDetails(@Query("lat") lat: Double?,
                                  @Query("lon") lon: Double?,
                                  @Query("units") units:String,
                                  @Query("lang") lang: String,
                                  @Query("exclude") exclude:String=MINUTELY,
                                  @Query("appid") appid:String=API_KEY
    ): Response<WeatherResponse>

}
