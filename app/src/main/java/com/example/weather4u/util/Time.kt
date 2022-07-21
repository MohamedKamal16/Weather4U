package com.example.weather4u.util

import java.util.*


object Time {
    fun getAppTime():Date{
        return Calendar.getInstance().time
    }

        fun timeConverter(timestamp: Long?) : String{
            val sdf = java.text.SimpleDateFormat("hh:mm a")
            val date = java.util.Date(timestamp!! * 1000)

            return sdf.format(date)
        }

        fun timeConverterToDate(timestamp: Long?) : String{
            val sdf = java.text.SimpleDateFormat("EEE',' MMM d")
            val date = java.util.Date(timestamp!! * 1000)

            return sdf.format(date)
        }
        fun currentTime(timestamp: Long?) : String{
            val sdf = java.text.SimpleDateFormat("dd-MM-yyyy HH:mm a")
            val date = java.util.Date(timestamp?.times(1000) ?: 1)

            return sdf.format(date)
        }
    fun time(timestamp: Long?) : String{
        val sdf = java.text.SimpleDateFormat("HH:mm a")
        val date = java.util.Date(timestamp?.times(1000) ?: 1)

        return sdf.format(date)
    }
    }
