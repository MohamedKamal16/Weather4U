package com.example.weather4u.util

object Icon {
        fun getIcon(icon: String?): String {
            return "http://openweathermap.org/img/w/${icon}.png"
        }
    }
