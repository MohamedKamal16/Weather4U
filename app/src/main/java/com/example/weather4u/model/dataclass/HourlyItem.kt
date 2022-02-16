package com.example.weather4u.model.dataclass

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.example.weather4u.model.dataclass.typeConvertor.WeatherTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(WeatherTypeConverter::class)
data class HourlyItem(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Double? = null,

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Double? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null,

	@field:SerializedName("snow")
	@Embedded(prefix = "snow_")
	val snow: Snow? = null,

	@field:SerializedName("rain")
	@Embedded(prefix = "rain_")
	val rain: Rain? = null
)