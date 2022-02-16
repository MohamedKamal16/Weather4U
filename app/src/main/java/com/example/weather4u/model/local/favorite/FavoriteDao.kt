package com.example.weather4u.model.local.favorite

import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM Favorite")
    fun getAllFev(): List<FavoriteEntity>

    @Delete
    suspend fun delete(favorite: FavoriteEntity)
}