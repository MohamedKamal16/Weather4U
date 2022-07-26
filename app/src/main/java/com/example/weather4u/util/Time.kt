package com.example.weather4u.util

import java.util.*


object Time {
    fun isTimeAmOrPm(): String {
        val time = getAppTime()
        val a = time.get(Calendar.AM_PM)
        return if (a == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }
    }

    fun getAppTime(): Calendar {
        return Calendar.getInstance()
    }

    fun timeConverter(timestamp: Long?): String {
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        val date = Date(timestamp!! * 1000)

        return sdf.format(date)
    }

    //day/month /(day as number)
    fun timeConverterToDate(timestamp: Long?): String {
        val sdf = java.text.SimpleDateFormat("EEE',' MMM d")
        val date = Date(timestamp!! * 1000)

        return sdf.format(date)
    }

    fun currentTime(timestamp: Long?): String {
        val sdf = java.text.SimpleDateFormat("dd-MM-yyyy HH:mm a")
        val date = Date(timestamp?.times(1000) ?: 1)

        return sdf.format(date)
    }

    fun time(timestamp: Long?): String {
        val sdf = java.text.SimpleDateFormat("HH:mm a")
        val date = Date(timestamp?.times(1000) ?: 1)

        return sdf.format(date)
    }
}
