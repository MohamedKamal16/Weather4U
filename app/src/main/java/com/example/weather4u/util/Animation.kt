package com.example.weather4u.util


import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView

object Animation {

     fun changeWeather(weatherView:WeatherView,weatherStatus:String?) {
        lateinit var weather: PrecipType

        var weatherSpeed = 0
        var weatherParticles = 0f
        //Randomly select a weather
        when (weatherStatus) {
              "Snow" -> {
                weather = PrecipType.SNOW
                weatherParticles = 10f
                weatherSpeed = 200
            }
            "Rain" -> {
                weather = PrecipType.RAIN
                weatherParticles = 150f
                weatherSpeed = 300
            }
            else -> {
                weather = PrecipType.CLEAR
            }
        }
        weatherView.apply {
            setWeatherData(weather)
            speed = weatherSpeed // How fast
            emissionRate = weatherParticles // How many particles
            angle = -20// The angle of the fall
            fadeOutPercent = .9f // When to fade out (0.0f-1.0f)

        }
    }



}