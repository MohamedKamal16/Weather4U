package com.example.weather4u.ui.adabter.favorite

/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.R
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time
import com.example.weather4u.viewModel.FavoriteFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DailyFavAdapter(var dailyList: ArrayList<DailyItem>, val viewModel: FavoriteFragmentViewModel,val context: Context) : RecyclerView.Adapter<DailyFavAdapter.DailyViewHolder>() {



    fun updateHoursList(dailyItem: List<DailyItem>) {
        dailyList.clear()
        dailyList.addAll(dailyItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = DailyViewHolder(
        LayoutInflater.from(context).inflate(R.layout.daily_row, parent, false)
    )


    override fun getItemCount(): Int {
        return dailyList.size
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        holder.time.text = Time.timeConverter(dailyList[position].sunrise?.toLong())
        holder.day.text = Time.timeConverterToDate(dailyList[position].dt)
        holder.temp.text = (dailyList[position].temp?.day)?.toInt().toString()
        holder.tempMin.text = (dailyList[position].temp?.min)?.toInt().toString()
        holder.wind.text = (dailyList[position].windSpeed)?.toInt().toString()
        holder.humidity.text = (dailyList[position].humidity).toString()
        holder.cloud.text = (dailyList[position].clouds).toString()
        holder.pressure.text = (dailyList[position].pressure).toString()
        holder.conditoin.text = (dailyList[position].weather?.get(0)?.description).toString()

        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(holder.cond.context).load(Icon.getIcon(dailyList[position].weather?.get(0)?.icon))
                .into(holder.cond)
        }


    }

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView = itemView.findViewById(R.id.degree_max)
        var tempMin: TextView = itemView.findViewById(R.id.degree_min)
        var time: TextView = itemView.findViewById(R.id.daily_time)
        var day: TextView = itemView.findViewById(R.id.daily_time2)
        var wind: TextView = itemView.findViewById(R.id.tv_windValue)
        var humidity: TextView = itemView.findViewById(R.id.tv_humValue)
        var cloud: TextView = itemView.findViewById(R.id.tv_cloudValue)
        var pressure: TextView = itemView.findViewById(R.id.tv_pressvalue)
        var conditoin: TextView = itemView.findViewById(R.id.tv_cond)
        var cond: ImageView = itemView.findViewById(R.id.imageView2)


    }





}


*/