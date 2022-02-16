package com.example.weather4u.view.adabter.favorite

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.R
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.util.Fav_LATITUDE
import com.example.weather4u.util.Fav_LONGITUDE
import com.example.weather4u.view.activity.FavoriteWeatherActivity
import com.example.weather4u.viewModel.FavoriteFragmentViewModel
import java.util.ArrayList


class FavoriteAdapter(private var favoriteData: ArrayList<FavoriteEntity>, var viewModel:FavoriteFragmentViewModel, var context:Context) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {


    fun updateList(newList: List<FavoriteEntity>) {
        favoriteData.clear()
        favoriteData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FavoriteViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_row, parent, false)
    )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.locationText.text=favoriteData[position].title

        holder.deletebtn.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setMessage(R.string.alertDeleteMessage)

            builder.setPositiveButton(R.string.yes) { _, _ ->
                this.viewModel.delete(favoriteData[position])
                this.favoriteData.remove(favoriteData[position])
                this.notifyDataSetChanged()
            }
            builder.setNegativeButton(R.string.no, null)
            builder.show()

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(this.context, FavoriteWeatherActivity::class.java)
            intent.putExtra(Fav_LATITUDE, favoriteData[position].lat)
            intent.putExtra(Fav_LONGITUDE, favoriteData[position].lon)
            this.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return favoriteData.size
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locationText: TextView = itemView.findViewById(R.id.favoriteTitleLbl)
        var deletebtn: Button = itemView.findViewById(R.id.FavDeleteButton)




        }


    }
