package com.example.weather4u.model.local.weatherRoom

import androidx.room.*
import com.example.weather4u.model.dataclass.AlertsItem
import com.example.weather4u.model.dataclass.Current
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.model.dataclass.typeConvertor.AlertTypeConverter
import com.example.weather4u.model.dataclass.typeConvertor.DailyTypeConverter
import com.example.weather4u.model.dataclass.typeConvertor.HourlyTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["lon", "lat"])
@JvmSuppressWildcards
@TypeConverters(AlertTypeConverter::class, DailyTypeConverter::class, HourlyTypeConverter::class)
data class WeatherResponse(

    @field:SerializedName("alerts")
	var alerts: List<AlertsItem?>? = null,

    @field:SerializedName("current")
	@Embedded(prefix = "current_")
	var current: Current? = null,

    @field:SerializedName("timezone")
	var timezone: String? = null,

    @field:SerializedName("timezone_offset")
	var timezoneOffset: Int? = null,

    @field:SerializedName("daily")
	var daily: List<DailyItem?>? = null,

    @field:SerializedName("lon")
	var lon: Double,

    @field:SerializedName("lat")
	var lat: Double,

    @field:SerializedName("hourly")
	var hourly: List<HourlyItem?>? = null

)

