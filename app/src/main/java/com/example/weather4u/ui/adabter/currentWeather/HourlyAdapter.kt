package com.example.weather4u.ui.adabter.currentWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.databinding.HourlyRowBinding
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HourlyAdapter(private var hoursList: MutableList<HourlyItem?>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    fun updateHoursList(hoursItem: List<HourlyItem?>) {
        hoursList.clear()
        hoursList.addAll(hoursItem)
        notifyDataSetChanged()
    }

    inner class HourlyViewHolder(val binding: HourlyRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HourlyViewHolder(
            HourlyRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return hoursList.size
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        with(holder.binding) {
            hourlyTimeHr.text = Time.timeConverter(hoursList[position]?.dt?.toLong())
            degreeMaxHr.text = (hoursList[position]?.temp)?.toInt().toString()
            tvCondHr.text = (hoursList[position]?.weather?.get(0)?.description).toString()
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(imageView3.context)
                    .load(Icon.getIcon(hoursList[position]?.weather?.get(0)?.icon)).into(imageView3)
            }
        }
    }
}
/*
//Another data but deleted
tvWindValueHr.text = (hoursList[position]?.windSpeed)?.toInt().toString()
tvHumValueHr.text = (hoursList[position]?.humidity).toString()
tvCloudValueHr.text = (hoursList[position]?.clouds).toString()
tvPressvalueHr.text = (hoursList[position]?.pressure).toString()
*/

