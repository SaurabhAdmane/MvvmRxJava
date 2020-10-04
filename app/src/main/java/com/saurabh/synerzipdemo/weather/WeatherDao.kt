package com.saurabh.synerzipdemo.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saurabh.synerzipdemo.model.SynerzipDataEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by SaurabhA on 04,October,2020
 */
@Dao
interface WeatherDao {

    @Insert
    fun insertWeather(data: SynerzipDataEntity): Completable

    @Query("delete from tbl_weather_report where city_id = :cityId")
    fun deleteWeather(cityId: String): Int

    @Query("select * from tbl_weather_report where city_id = :cityId")
    fun getWeatherForCityId(cityId: String): Single<List<SynerzipDataEntity>>

}