package com.saurabh.demo.weather

import android.util.Log
import com.saurabh.demo.model.SynerzipDataEntity
import com.saurabh.demo.networking.WeatherDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by SaurabhA on 04,October,2020
 */
class DBManager {

    fun insertData(data: SynerzipDataEntity) {
        WeatherDatabase.getInstance().weatherDao().insertWeather(data).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(javaClass.name, "Insert Success")
            }, { throwable ->
                Log.e(javaClass.name, "Insert error")
            })
    }

    fun deleteData(cityId: String) {
        WeatherDatabase.getInstance().weatherDao().deleteWeather(cityId)
    }

    fun getDataForCityId(cityId: String) {
        // todo check in localDB if not present then hit the API, for that i have to use flatMap
        WeatherDatabase.getInstance().weatherDao().getWeatherForCityId(cityId)
    }
}