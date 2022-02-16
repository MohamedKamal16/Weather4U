package com.example.weather4u.view.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weather4u.databinding.ActivityAddAlertBinding
import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.util.PLACE_API_KEY
import com.example.weather4u.util.REQUEST_CODE
import com.example.weather4u.viewModel.AlertFragmentViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class AddAlert : AppCompatActivity() {
    private lateinit var binding:ActivityAddAlertBinding
    private lateinit var viewModel:AlertFragmentViewModel
     val newAlert = AlertEntity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AlertFragmentViewModel::class.java)

        binding.addBtn.setOnClickListener(View.OnClickListener {
            addAlert()
        })

        binding.timeLbl.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val timeSetListener =
                TimePickerDialog.OnTimeSetListener(fun(timePicker: TimePicker , hour: Int, minute: Int) {
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                binding.timeLbl.text = "$hour : $minute"
            })

            TimePickerDialog(this, timeSetListener
                ,calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                , true).show()
        })
        binding.datelbl.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val datelisten= DatePickerDialog.OnDateSetListener(fun(datePicker:DatePicker,year:Int,month:Int,day:Int){
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,month)
                c.set(Calendar.DAY_OF_MONTH,day)
                binding.datelbl.text="$year :$month :$day" })

            DatePickerDialog(this,datelisten,c.get(Calendar.YEAR)
                ,c.get(Calendar.MONTH)
                ,c.get(Calendar.DAY_OF_MONTH)
            ).show()


        })


        binding.location.setOnClickListener(View.OnClickListener {
            Places.initialize(this, PLACE_API_KEY)
            val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this)
            startActivityForResult(intent, REQUEST_CODE)
        })



}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        if (place.latLng!=null){
                            newAlert.lat = place.latLng.latitude
                            newAlert.lon = place.latLng.longitude

                        }

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {

                }
                Activity.RESULT_CANCELED -> {

                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun addAlert() {

        newAlert.alertTime =binding.timeLbl.text.toString()
        newAlert.alertDay= binding.datelbl.text.toString()
        newAlert.alertEvent =binding.alertEventSpinner.selectedItem.toString()
        newAlert.  enabled = true
        newAlert. event = ""
        newAlert.start = 0L
        newAlert. end = 0L
        newAlert.description = ""

        viewModel.insert(newAlert)
        finish()

    }


}
