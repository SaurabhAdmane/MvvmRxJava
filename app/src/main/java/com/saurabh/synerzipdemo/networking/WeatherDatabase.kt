package com.saurabh.synerzipdemo.networking

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saurabh.synerzipdemo.SynerzipApp
import com.saurabh.synerzipdemo.model.SynerzipDataEntity
import com.saurabh.synerzipdemo.weather.WeatherDao

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
@Database(entities = [SynerzipDataEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(SynerzipApp.context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): WeatherDatabase {
            val DB_NAME = "weather.db"
            return Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java, DB_NAME
            ).allowMainThreadQueries()
                .build()
        }
    }
}