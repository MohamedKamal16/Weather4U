package com.example.weather4u.model.dataclass.typeConvertor

import androidx.room.TypeConverter
import com.example.weather4u.model.dataclass.HourlyItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//convert data for Room
class HourlyTypeConverter {
    companion object{
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
    }
}