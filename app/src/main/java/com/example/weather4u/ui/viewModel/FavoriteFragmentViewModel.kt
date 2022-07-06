package com.example.weather4u.ui.viewModel


import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel@Inject constructor(
    private val repo: WeatherRepository, application: Application) :BaseViewModel(application){

    fun insertUpdate(favorite: FavoriteEntity)=viewModelScope.launch {
        repo.insert(favorite)
    }

    fun getAll()= repo.getAllFev()

    fun delete(favorite: FavoriteEntity)=
        viewModelScope.launch {
            repo.delete(favorite)
        }

}
