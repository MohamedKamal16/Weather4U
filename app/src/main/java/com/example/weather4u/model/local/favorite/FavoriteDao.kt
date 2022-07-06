package com.example.weather4u.model.local.favorite

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFev(): LiveData<List<FavoriteEntity>>

    @Delete
    suspend fun delete(favorite: FavoriteEntity)
}