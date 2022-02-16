package com.example.weather4u.model.dataclass.typeConvertor

import androidx.room.TypeConverter
import com.example.weather4u.model.dataclass.AlertsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//convert data for Room(List)
class AlertTypeConverter {
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
    }
}