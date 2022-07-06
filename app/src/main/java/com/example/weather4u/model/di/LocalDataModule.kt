package com.example.weather4u.model.di

import android.content.Context
import androidx.room.Room
import com.example.weather4u.model.local.WeatherDataBase
import com.example.weather4u.util.Constant.ROOM_DATABASE_NAME
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideRunDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        ROOM_DATABASE_NAME
    ).build()



    @Singleton
    @Provides
    fun provideWeather(db: WeatherDataBase) = db.getWeatherDao()

    @Singleton
    @Provides
    fun provideFavorite(db: WeatherDataBase) = db.getFavoriteDao()

    @Singleton
    @Provides
    fun provideAlert(db: WeatherDataBase) = db.getAlertDao()

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext app:Context
    )= FusedLocationProviderClient(app)




}