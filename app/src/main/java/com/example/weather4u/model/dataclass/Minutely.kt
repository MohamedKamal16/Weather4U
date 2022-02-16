package com.example.weather4u.model.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Minutely (@SerializedName("dt") @Expose var dt: Int?,
                     @SerializedName("precipitation") @Expose var precipitation: Double? )

