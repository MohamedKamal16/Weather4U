package com.example.weather4u.util.workManger

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.example.weather4u.R
import com.example.weather4u.repository.WeatherRepository
import com.example.weather4u.util.Constant.NOTIFICATION_ID
import com.example.weather4u.util.Constant.WORKER_LAT
import com.example.weather4u.util.Constant.WORKER_LON
import com.example.weather4u.util.Constant.WORKER_Lang
import com.example.weather4u.util.Constant.WORKER_Unit
import com.example.weather4u.util.Icon
import com.example.weather4u.util.Notification.createNotification
import com.example.weather4u.util.Time.time
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class WeatherWork @AssistedInject constructor(
    @Assisted  context: Context,
    @Assisted  workerParams: WorkerParameters,
    private val repo: WeatherRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
       val lat =inputData.getDouble(WORKER_LAT,0.0)
        val lon = inputData.getDouble(WORKER_LON,0.0)
        val unit = inputData.getString(WORKER_Unit)?:"metric"
        val lang = inputData.getString(WORKER_Lang)?:"en"

    Log.d("workStart","result ${lat}/${lon}")
        val response =repo.getWeather(lat,lon, unit, lang)
        if (response.isSuccessful){
            response.body().let { body->
                Log.d("workStart","call success")

                val contentView= RemoteViews(applicationContext.packageName, R.layout.custom_notification)
                val notification=createNotification(applicationContext,contentView)

                contentView.setTextViewText(R.id.tv_weather,body?.current?.weather?.get(0)?.description)
                contentView.setTextViewText(R.id.tv_city,body?.timezone?.substringAfter("/"))
                contentView.setTextViewText(R.id.tv_time, time(body?.current?.dt?.toLong()))
             //   contentView.setImageViewBitmap(R.id.icon,stringToBitMap(getIcon(body?.current?.weather?.get(0)?.icon)))
                val target = NotificationTarget(applicationContext,R.id.icon, contentView,notification,NOTIFICATION_ID)
                Glide.with(applicationContext)
                    .asBitmap()
                    .load(body?.current?.weather?.get(0)?.icon?.let { Icon.getIcon(it) })
                    .centerCrop()
                    .into(target)
                //start notifcation
                NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)

            }
            return Result.success()
        }
        return Result.failure()
    }


}