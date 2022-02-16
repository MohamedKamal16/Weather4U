package com.example.weather4u.model.local.alertRoom

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(
    entities = [AlertEntity::class],
    version = 1,
    exportSchema = false

)
abstract class AlertDataBase:RoomDatabase() {
    abstract fun getAlertDao(): AlertDao
}


