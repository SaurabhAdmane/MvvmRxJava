package com.saurabh.demo.weather.dataproviders

import com.saurabh.demo.model.WeatherResponseBean
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by SaurabhA on 04,October,2020
 */
interface WeatherDataProvider {

    fun getWeatherDisposable(
        cityName: String,
        success: Consumer<WeatherResponseBean>,
        error: Consumer<Throwable>
    ): Disposable
}