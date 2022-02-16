package com.example.weather4u.util.workManger

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

//logic
object Worker {
  /*  const val tripId = "TripId"
    @Throws(ParseException::class)

    //logic
  fun setWorkers(tripList: List<Trip?>) {
        WorkManager.getInstance().cancelAllWork()
        var formater: SimpleDateFormat
        var date: Date
        var tripDate: Long

        for (trip in tripList) {

            val dated: String = trip.getDate().toString() + ":" + trip.getTime()
            formater = SimpleDateFormat("dd-MM-yy:HH:mm", Locale.getDefault())
            date = formater.parse(dated)
            tripDate = date.time

            val builder = Data.Builder()
            builder.putInt(tripId, trip.getId())

            //request Type
            val oneTimeWorkRequest: WorkRequest =
                OneTimeWorkRequest.Builder(AlarmWork::class.java)
                    .setInitialDelay(tripDate - Calendar.getInstance().timeInMillis,TimeUnit.MILLISECONDS
                    )
                    .setInputData(builder.build())
                    .build()
            WorkManager.getInstance().enqueue(oneTimeWorkRequest)
        }
    }*/
}
