package com.example.weather4u.ui.adabter.currentWeather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.R
import com.example.weather4u.databinding.CurrentLayoutBinding
import com.example.weather4u.model.dataclass.WeatherResponse
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time.currentTime

class CurrentAdapter(
    var weatherResponse: WeatherResponse?,
    val context: Context,
    val unit: String
) :
    RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {

    fun updateCurrent(item: WeatherResponse) {
        weatherResponse = item
        notifyDataSetChanged()
    }

    inner class CurrentViewHolder(val binding: CurrentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrentViewHolder(
            CurrentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        with(holder.binding) {
            city.text = weatherResponse?.timezone?.substringAfter("/")
            dateTime.text = currentTime(weatherResponse?.current?.dt?.toLong())
            //  (context.getText(R.string.wind).toString() + (weatherResponse?.current?.windSpeed?.toInt()).toString()).also { windSpeed.text = it }
            tvDesc.text = weatherResponse?.current?.weather?.get(0)?.description

            Glide.with(iconWeather)
                .load(weatherResponse?.current?.weather?.get(0)?.icon?.let { Icon.getIcon(it) })
                .centerCrop()
                .into(iconWeather)

            when (unit) {
                "imperial" -> {
                    temp.text = weatherResponse?.current?.temp?.toInt().toString() + context.getText(R.string.fahrenheit)
                    //  windSpeed.text=weatherResponse?.current?.windSpeed?.toInt().toString() +context.getText(R.string.mileHr)

                }
                "metric" -> {
                    temp.text = weatherResponse?.current?.temp?.toInt().toString() + context.getText(R.string.celsius)
                    //  windSpeed.text=weatherResponse?.current?.windSpeed?.toInt().toString() +" "+context.getText(R.string.meterSec)

                }
                else ->
                    temp.text = weatherResponse?.current?.temp?.toInt().toString() + context.getText(R.string.kelvin)
            }

        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}