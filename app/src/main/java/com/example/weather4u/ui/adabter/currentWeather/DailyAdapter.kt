package com.example.weather4u.ui.adabter.currentWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.databinding.DailyRowBinding
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time
import java.util.*

class DailyAdapter(private var dailyList: MutableList<DailyItem?>) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    fun updateDailyList(dailyItem: List<DailyItem?>) {
        dailyList.clear()
        dailyList.addAll(dailyItem)
        notifyDataSetChanged()
    }

    inner class DailyViewHolder( val binding: DailyRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        DailyViewHolder(DailyRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return dailyList.size
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        with(holder.binding){
            dailyTime.text = Time.timeConverter(dailyList[position]?.sunrise?.toLong())
            dailyTime2.text =Time.timeConverterToDate(dailyList[position]?.dt)
            degreeMax.text = (dailyList[position]?.temp?.day)?.toInt().toString()
            degreeMin.text = (dailyList[position]?.temp?.min)?.toInt().toString()
            tvWindValue.text = (dailyList[position]?.windSpeed)?.toInt().toString()
            tvHumValue.text = (dailyList[position]?.humidity).toString()
            tvCloudValue.text = (dailyList[position]?.clouds).toString()
            tvPressvalue.text = (dailyList[position]?.pressure).toString()
            tvCond.text = (dailyList[position]?.weather?.get(0)?.description).toString()
            Glide.with(imageView2.context).load(Icon.getIcon(dailyList[position]?.weather?.get(0)?.icon))
                    .into(imageView2)
            }
        }
    }





