package com.example.weather4u.model.local.alertRoom

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Alert")
class AlertEntity {
    @PrimaryKey(autoGenerate = true)
    var id :Int = 0

    var start: Long = 0L
    var end: Long =0L
    var lat: Double=0.0
    var lon: Double=0.0
    var enabled: Boolean = false

    lateinit  var alertTime: String
    lateinit var alertDay: String
    lateinit var alertEvent: String
    lateinit var description: String
    lateinit var event: String

}

