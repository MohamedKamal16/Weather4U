package com.example.weather4u.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.model.local.alertRoom.AlertInstance
import com.example.weather4u.model.local.favorite.FavoriteInstance
import com.example.weather4u.model.local.weatherRoom.LocalDatabase
import com.example.weather4u.model.repository.WeatherRepository

class AlertFragmentViewModel (application: Application) : AndroidViewModel(application){

    private val localData= LocalDatabase.getInstance(application)
    private val localData2= FavoriteInstance.getInstance(application)
    private val localData3= AlertInstance.getInstance(application)
    val repository : WeatherRepository = WeatherRepository(localData,localData2,localData3)

    fun insert(alert: AlertEntity){
        repository.insertAlert(alert)
        Log.d("Alzhoor","haaaaaaaaaaaaaaaa")
    }
    fun delete(alert: AlertEntity)=repository.deleteAlert(alert)
    //get all data from room and post it to live data
    fun getAllAlert() =repository.getAllAlert()
    //get all data from live data in observe
    fun getAlertInfo() =repository.getAlertInfo()

}