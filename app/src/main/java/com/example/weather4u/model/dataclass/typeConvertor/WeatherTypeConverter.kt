package com.example.weather4u.model.dataclass.typeConvertor

import androidx.room.TypeConverter
import com.example.weather4u.model.dataclass.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//convert data for Room
class WeatherTypeConverter {
    companion object{
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