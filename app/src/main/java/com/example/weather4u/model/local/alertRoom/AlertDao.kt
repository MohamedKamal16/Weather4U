
package com.example.weather4u.model.local.alertRoom

import androidx.room.*

@Dao
interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alert: AlertEntity)

    @Query("SELECT * FROM AlertEntity")
    fun getAll(): List<AlertEntity>

    @Query("SELECT * FROM AlertEntity WHERE enabled=:enabled and alertTime=:alertTime")
    fun getSome(alertTime: String, enabled: Boolean): List<AlertEntity>

    @Delete
    suspend fun delete(alert: AlertEntity)
}