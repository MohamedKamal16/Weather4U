package com.example.weather4u.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather4u.model.dataclass.typeConvertor.Converters
import com.example.weather4u.model.local.favorite.FavoriteDao
import com.example.weather4u.model.local.favorite.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false

)
@TypeConverters(Converters::class)
abstract class WeatherDataBase:RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}


