package com.example.weather4u.ui.adabter.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weather4u.R
import com.example.weather4u.databinding.FavoriteRowBinding
import com.example.weather4u.model.local.favorite.FavoriteEntity
import com.example.weather4u.util.Constant.Fav_LATITUDE
import com.example.weather4u.util.Constant.Fav_LONGITUDE


class FavoriteAdapter( var favoriteData: MutableList<FavoriteEntity>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {

    inner class FavoriteViewHolder(val binding: FavoriteRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(newList: List<FavoriteEntity>) {
        favoriteData.clear()
        favoriteData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FavoriteViewHolder(
        FavoriteRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder){
            binding.favoriteTitleLbl.text=favoriteData[position].title

            itemView.setOnClickListener {
                val bundle=Bundle().apply {
                    putDouble(Fav_LATITUDE, favoriteData[position].lat!!)
                    putDouble(Fav_LONGITUDE, favoriteData[position].lon!!)
                }
                itemView.findNavController().navigate(R.id.favoriteWeatherFragment,bundle)

            }
        }


    }

    override fun getItemCount(): Int {
        return favoriteData.size
    }


    }
