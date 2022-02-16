package com.example.weather4u.view.adabter.alert

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.R
import com.example.weather4u.model.local.alertRoom.AlertEntity
import com.example.weather4u.viewModel.AlertFragmentViewModel
import java.util.ArrayList

class AlertAdapter(var data: ArrayList<AlertEntity>,val context:Context,val viewModel: AlertFragmentViewModel): RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    fun updateList(newList: List<AlertEntity>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
      =AlertViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.alert__row, parent, false))

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.alertTimeLbl.text = data[position].alertTime
        holder. alertEventLbl.text = data[position].alertEvent
        holder. alertSwitch.isChecked = data[position].enabled
        holder. alertDayLbl.text = data[position].alertDay




        holder. alertSwitch.setOnClickListener {
            data[position].enabled = holder.alertSwitch.isChecked
            this.viewModel.insert(data[position])
            true
        }


        holder.  deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)

            val pref = PreferenceManager.getDefaultSharedPreferences(this.context.applicationContext)
            val language = pref.getString("language", "en")!!

            if (language == "en"){
                builder.setTitle("Warning")
                builder.setMessage("Are you sure you want delete this?")


                builder.setPositiveButton("Yes") { _, _ ->
                    this.viewModel.delete(data[position])
                    this.data.remove(data[position])
                    this.notifyDataSetChanged()
                }

                builder.setNegativeButton("No", null)
                builder.show()
            }else {
                builder.setTitle("تنبيه")
                builder.setMessage("هل تريد حذف هذا المنبه؟")



                builder.setPositiveButton("تأكيد") { _, _ ->
                    this.viewModel.delete(data[position])
                    this.data.remove(data[position])
                    this.notifyDataSetChanged()
                }

                builder.setNegativeButton("إلغاء", null)
                builder.show()

            }
        }
    }



    override fun getItemCount(): Int {
       return data.size
    }

    class AlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val alertTimeLbl: TextView = itemView.findViewById(R.id.alertTimeLbl)
         val alertDayLbl:TextView = itemView.findViewById(R.id.alertDayLbl)
         val alertEventLbl :TextView= itemView.findViewById(R.id.alertEventLbl)
         val alertSwitch:Switch = itemView.findViewById(R.id.alertSwitch)
         val deleteButton:Button = itemView.findViewById(R.id.deleteButton)

    }
}

