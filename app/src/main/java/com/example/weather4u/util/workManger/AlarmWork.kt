package com.example.weather4u.util.workManger

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlarmWork(var context: Context, var workerParams: WorkerParameters) : Worker(context, workerParams){

    override fun doWork(): Result {
     /*   try { val intent = Intent(context, MainNotification::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val trip: String = getInputData().getString("TripId")
            //Pending Intent
            println("trip")
            println(trip)
            intent.putExtra("TripId", trip)
            context!!.startActivity(intent)
        } catch (e: Exception) {
            e.message
        }*/
        return Result.success()
    }
}