package com.example.weather4u.model.dataclass.typeConvertor

import androidx.room.TypeConverter
import com.example.weather4u.model.dataclass.AlertsItem
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.model.dataclass.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromAlertsList(value: MutableList<AlertsItem>?): String {
            val type = object : TypeToken<MutableList<AlertsItem>>() {}.type
            return Gson().toJson(value, type)
        }
        @TypeConverter
        @JvmStatic
        fun toAlertsList(value: String?): MutableList<AlertsItem>? {
            if (value == null) {
                return null
            }
            val type = object : TypeToken<MutableList<AlertsItem>>() {}.type
            return Gson().fromJson(value, type)
        }


        //to insert weather in table as gson
        @TypeConverter
        @JvmStatic
        fun fromDailyList(value: MutableList<DailyItem>): String {
            val type = object : TypeToken<MutableList<DailyItem>>() {}.type
            return Gson().toJson(value, type)
        }
        //to take it out from table as list of weather
        @TypeConverter
        @JvmStatic
        fun toDailyList(value: String): MutableList<DailyItem> {
            val type = object : TypeToken<MutableList<DailyItem>>() {}.type
            return Gson().fromJson(value, type)
        }

        //to insert weather in table as gson
        @TypeConverter
        @JvmStatic
        fun fromHourlyList(value: MutableList<HourlyItem>): String {
            val type = object : TypeToken<MutableList<HourlyItem>>() {}.type
            return Gson().toJson(value, type)
        }
        //to take it out from table as list of weather
        @TypeConverter
        @JvmStatic
        fun toHourlyList(value: String): MutableList<HourlyItem> {
            val type = object : TypeToken<MutableList<HourlyItem>>() {}.type
            return Gson().fromJson(value, type)
        }
        //to insert weather in table as gson
        @TypeConverter
        @JvmStatic
        fun fromWeatherList(value: MutableList<WeatherItem>): String {
            val type = object : TypeToken<MutableList<WeatherItem>>() {}.type
            return Gson().toJson(value, type)
        }
        //to take it out from table as list of weather
        @TypeConverter
        @JvmStatic
        fun toWeatherList(value: String): MutableList<WeatherItem> {
            val type = object : TypeToken<MutableList<WeatherItem>>() {}.type
            return Gson().fromJson(value, type)
        }
    }
}