package com.example.weather4u.model.dataclass.typeConvertor

import androidx.room.TypeConverter
import com.example.weather4u.model.dataclass.DailyItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//convert data for Room(List)
class DailyTypeConverter {
    companion object{
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
    }
}