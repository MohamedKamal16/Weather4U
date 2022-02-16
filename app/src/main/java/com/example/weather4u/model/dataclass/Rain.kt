package com.example.weather4u.model.dataclass

import com.google.gson.annotations.SerializedName

data class Rain(

	@field:SerializedName("1h")
	val jsonMember1h: Double? = null
)