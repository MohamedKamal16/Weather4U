package com.example.weather4u.model.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class FavoriteEntity(
    var title: String?,
    var lat: Double?,
    var lon: Double?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
