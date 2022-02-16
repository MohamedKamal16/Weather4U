package com.example.weather4u.model.dataclass

import com.google.gson.annotations.SerializedName

data class AlertsItem(

	@field:SerializedName("start")
	var start: Int? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("sender_name")
	var senderName: String? = null,

	@field:SerializedName("end")
	var end: Int? = null,

	@field:SerializedName("event")
	var event: String? = null
)