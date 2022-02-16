package com.example.weather4u.view.activity

import android.content.Context
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather4u.R
import com.example.weather4u.databinding.ActivityFavoriteWeatherBinding
import com.example.weather4u.model.dataclass.DailyItem
import com.example.weather4u.model.dataclass.HourlyItem
import com.example.weather4u.model.local.weatherRoom.WeatherResponse
import com.example.weather4u.util.Fav_LATITUDE
import com.example.weather4u.util.Fav_LONGITUDE
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Time
import com.example.weather4u.view.adabter.favorite.DailyFavAdapter
import com.example.weather4u.view.adabter.favorite.HourlyFavAdapter
import com.example.weather4u.viewModel.FavoriteFragmentViewModel
import kotlinx.android.synthetic.main.activity_favorite_weather.city
import kotlinx.android.synthetic.main.activity_favorite_weather.clouds
import kotlinx.android.synthetic.main.activity_favorite_weather.data_humidity
import kotlinx.android.synthetic.main.activity_favorite_weather.data_pressure
import kotlinx.android.synthetic.main.activity_favorite_weather.data_visibility
import kotlinx.android.synthetic.main.activity_favorite_weather.date_time
import kotlinx.android.synthetic.main.activity_favorite_weather.feels_like
import kotlinx.android.synthetic.main.activity_favorite_weather.temp
import kotlinx.android.synthetic.main.activity_favorite_weather.tv_desc
import kotlinx.android.synthetic.main.activity_favorite_weather.windSpeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class FavoriteWeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteWeatherBinding
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var viewModel: FavoriteFragmentViewModel
    private lateinit var loading: ProgressBar
    private lateinit var dailyListAdapter: DailyFavAdapter
    private lateinit var hourlyListAdapter : HourlyFavAdapter
    private var lat = 0.0
    private var lon = 0.0
    private var unit = String()
    private var language = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoriteWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dailyRecyclerView= binding.recyclerViewDaily
        hourlyRecyclerView=binding.recyclerViewHourly
        loading=binding.pbLoading

        lat=intent.getDoubleExtra(Fav_LATITUDE,0.0)
        lon=intent.getDoubleExtra(Fav_LONGITUDE,0.0)

        viewModel = ViewModelProvider(this)[FavoriteFragmentViewModel::class.java]
        dailyListAdapter = DailyFavAdapter(arrayListOf(),viewModel,this)
        hourlyListAdapter = HourlyFavAdapter(arrayListOf(),viewModel,this)


        viewModel.getWeatherFavLiveDat() .observe(this, Observer { data ->
            data?.let {
                data.let { currentDetail(it) }
                data.hourly?.let { it1 -> updateHourlyListUI(it1 as List<HourlyItem>) }
                data.daily?.let { it1 -> updateDailyListUI(it1 as List<DailyItem>) }

            }
        })
        viewModel.repository.loadingLiveData.observe(this) { showLoading(it) }

    }
    override fun onResume() {
        super.onResume()
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        unit = pref.getString("unit", "metric").toString()
        language = pref.getString("language", "en").toString()

        viewModel.loadFavData(lat,lon,language,unit)

        init()


    }


    private fun showLoading(flag: Boolean) {

        if(flag){
            loading.visibility = View.VISIBLE
        }else{
            loading.visibility = View.GONE
        }
    }
    private fun updateDailyListUI(it: List<DailyItem>) {
        dailyListAdapter.updateHoursList(it)
    }
    private fun updateHourlyListUI(it: List<HourlyItem>) {
        hourlyListAdapter.updateHoursList(it)
    }

    private fun currentDetail(weatherResponse: WeatherResponse) {
        CoroutineScope(Dispatchers.Main).launch{
            city.text = getCityName(this@FavoriteWeatherActivity,language,lat,lon,weatherResponse.timezone!!)
            date_time.text = Time.currentTime(weatherResponse.current?.dt?.toLong())
            tv_desc.text = weatherResponse.current?.weather?.get(0)?.description
            windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString()
            clouds.text = getText(R.string.clouds).toString()+ weatherResponse.current?.clouds.toString() + "%"
            data_humidity.text=weatherResponse.current?.humidity.toString()
            data_visibility.text=weatherResponse.current?.visibility.toString()
            data_pressure.text=weatherResponse.current?.pressure.toString()
            feels_like.text= weatherResponse.current?.feelsLike.toString()
            when (unit) {
                "imperial" ->
                { temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.fahrenheit)}"
                    windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString() + getText(R.string.mileHr)
                }
                "metric" ->{
                    temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.celsius)}"
                    windSpeed.text = getText(R.string.wind).toString() + (weatherResponse.current?.windSpeed?.toInt()).toString() + getText(R.string.meterSec)
                }
                else -> temp.text = weatherResponse.current?.temp?.toInt().toString() +"${getText(R.string.kelvin)}"
            }
        }
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(this@FavoriteWeatherActivity).load(weatherResponse.current?.weather?.get(0)?.icon?.let { Icon.getIcon(it) })
                .centerCrop()
                . into(binding.imageView)
        }
    }
    //crash if i put it in weather fragment view because null location Address Todo find way to solve it
    fun getCityName(context: Context, savedLang: String, lat: Double, lon:Double, timeZone:String):String{
        var locationAddress = ""
        val geocoder = Geocoder(context, Locale(savedLang))
        try {
            if(savedLang=="ar"){
                locationAddress = geocoder.getFromLocation(lat,lon,1)[0].adminArea ?: timeZone
                locationAddress += ", ${geocoder.getFromLocation(lat,lon,1)[0].countryName ?: timeZone}"
            }else{
                locationAddress = geocoder.getFromLocation(lat,lon,1)[0].adminArea ?: timeZone
                locationAddress += ", ${geocoder.getFromLocation(lat,lon,1)[0].countryName ?: timeZone}"}
        } catch (e: IOException){
            e.printStackTrace()
        }
        return locationAddress
    }

    private fun init() {
        dailyRecyclerView.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, true)
        dailyRecyclerView.adapter = dailyListAdapter
        hourlyRecyclerView.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, true)
        hourlyRecyclerView.adapter = hourlyListAdapter
    }

}