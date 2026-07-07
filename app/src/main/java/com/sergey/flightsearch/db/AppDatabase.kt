package com.sergey.flightsearch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.sergey.flightsearch.dao.AirportDao
import com.sergey.flightsearch.dao.FavoriteDao
import com.sergey.flightsearch.entity.Airport
import com.sergey.flightsearch.entity.Favorite

@Database(
    entities = [Airport::class, Favorite::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "flight_search.db"
            )
                .createFromAsset("flight_search.db")
                .build()
        }
    }
}