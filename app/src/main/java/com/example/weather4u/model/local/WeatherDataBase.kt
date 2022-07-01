package com.example.weather4u.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather4u.model.dataclass.typeConvertor.Converters
import com.example.weather4u.model.local.alertRoom.AlertDao
import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.model.local.favorite.FavoriteDao
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.model.local.weatherRoom.WeatherDAO
import com.example.weather4u.model.local.weatherRoom.WeatherResponse

@Database(
    entities = [WeatherResponse::class,FavoriteEntity::class,AlertEntity::class],
    version = 1,
    exportSchema = false

)
@TypeConverters(Converters::class)
abstract class WeatherDataBase:RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDAO
    abstract fun getFavoriteDao(): FavoriteDao
    abstract fun getAlertDao(): AlertDao
}


