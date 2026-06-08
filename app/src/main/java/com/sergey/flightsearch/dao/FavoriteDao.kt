package com.sergey.flightsearch.dao

import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}