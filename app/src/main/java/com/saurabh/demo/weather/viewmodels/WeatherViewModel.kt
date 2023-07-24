package com.saurabh.demo.weather.viewmodels

import android.util.Log
import com.saurabh.demo.base.BaseViewModel
import com.saurabh.demo.weather.WeatherManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created by SaurabhA on 04,October,2020
 */
class WeatherViewModel : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val manager = WeatherManager(compositeDisposable)

    fun hitAPI(value: String) {
        manager.getWeatherApi(value, Consumer { success ->
            Log.e("SUCCESS", "Success")
        }, Consumer { error ->
            Log.e("ERROR", "Error")
        })
    }

    override fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
            compositeDisposable.dispose()
        }
    }
}