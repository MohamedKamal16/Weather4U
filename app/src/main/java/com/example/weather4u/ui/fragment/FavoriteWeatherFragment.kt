package com.example.weather4u.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather4u.R
import com.example.weather4u.ui.viewModel.FavoriteWeatherViewModel

class FavoriteWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteWeatherFragment()
    }

    private lateinit var viewModel: FavoriteWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}