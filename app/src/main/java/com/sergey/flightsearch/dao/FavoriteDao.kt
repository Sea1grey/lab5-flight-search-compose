package com.sergey.flightsearch.dao

import androidx.room.*
import com.sergey.flightsearch.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("""
    SELECT COUNT(*) FROM favorite
    WHERE departure_code = :departure
    AND destination_code = :destination
""")
    suspend fun isFavorite(
        departure: String,
        destination: String
    ): Int

    @Query("""
    DELETE FROM favorite
    WHERE departure_code = :departure
    AND destination_code = :destination
""")
    suspend fun deleteFavorite(
        departure: String,
        destination: String
    )
}