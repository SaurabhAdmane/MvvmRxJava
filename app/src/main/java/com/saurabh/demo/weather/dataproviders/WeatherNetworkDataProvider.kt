package com.saurabh.demo.weather.dataproviders

import com.saurabh.demo.model.CustomErrorBean
import com.saurabh.demo.model.WeatherResponseBean
import com.saurabh.demo.networking.RestClient.getClient
import com.saurabh.demo.weather.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by SaurabhA on 04,October,2020
 */
class WeatherNetworkDataProvider : WeatherDataProvider {
    private val weatherService = getClient().create(WeatherService::class.java)

    override fun getWeatherDisposable(
        cityName: String,
        success: Consumer<WeatherResponseBean>,
        error: Consumer<Throwable>
    ): Disposable {
        return weatherService.getWeatherEndpoint(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response == null) {
                    error.accept(
                        CustomErrorBean(
                            99,
                            "Something went wrong"
                        )
                    )
                } else {
                    success.accept(response)
                }
            }, error)
    }
}