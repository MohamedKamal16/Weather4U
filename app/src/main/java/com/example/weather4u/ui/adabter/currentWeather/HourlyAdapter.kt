package com.example.weather4u.ui.adabter.currentWeather
/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.R
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time
import com.example.weather4u.viewModel.WeatherFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HourlyAdapter(private var hoursList: ArrayList<HourlyItem>, val viewModel: WeatherFragmentViewModel) : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>(){

    fun updateHoursList( hoursItem: List<HourlyItem>) {
        hoursList.clear()
        hoursList.addAll(hoursItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = HourlyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.hourly_row, parent, false)
    )


    override fun getItemCount(): Int {
        return hoursList.size
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder. time.text = Time.timeConverter(hoursList[position].dt?.toLong())
        holder.temp.text = (hoursList[position].temp)?.toInt().toString()
        holder. wind.text = (hoursList[position].windSpeed)?.toInt().toString()
        holder. humidity.text = (hoursList[position].humidity).toString()
        holder.cloud.text = (hoursList[position].clouds).toString()
        holder.pressure.text = (hoursList[position].pressure).toString()
        holder.conditoin.text=(hoursList[position].weather?.get(0)?.description).toString()
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.cond.context).load(Icon.getIcon(hoursList[position].weather?.get(0)?.icon)).into(holder.cond)
        }

    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val temp: TextView =itemView.findViewById(R.id.degree_maxHr)
     val time:TextView=itemView.findViewById(R.id.hourly_timeHr)
     val wind:TextView=itemView.findViewById(R.id.tv_windValueHr)
     val humidity:TextView=itemView.findViewById(R.id.tv_humValueHr)
     val cloud:TextView=itemView.findViewById(R.id.tv_cloudValueHr)
     val pressure:TextView=itemView.findViewById(R.id.tv_pressvalueHr)
     val conditoin:TextView=itemView.findViewById(R.id.tv_condHr)
     val cond: ImageView =itemView.findViewById(R.id.imageView3)



    }

}

*/