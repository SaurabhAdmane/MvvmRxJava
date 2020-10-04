package com.saurabh.synerzipdemo.weather

import com.saurabh.synerzipdemo.model.WeatherResponseBean
import com.saurabh.synerzipdemo.weather.dataproviders.WeatherNetworkDataProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created by SaurabhA on 04,October,2020
 */
class WeatherManager(private val compositeDisposable: CompositeDisposable?) {

    private val dataProvider: WeatherNetworkDataProvider = WeatherNetworkDataProvider()

    fun getWeatherApi(
        cityName: String,
        response: Consumer<WeatherResponseBean>,
        error: Consumer<Throwable>
    ) {
        compositeDisposable!!.add(
            dataProvider.getWeatherDisposable(
                cityName,
                Consumer { response.accept(it) },
                error
            )
        )
    }
}