package com.example.weather4u.util


object Time {

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
    }
