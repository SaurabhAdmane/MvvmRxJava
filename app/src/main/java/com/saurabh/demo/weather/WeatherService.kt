package com.saurabh.demo.weather

import com.saurabh.demo.model.WeatherResponseBean
import com.saurabh.demo.networking.SynerzipAPI
import com.saurabh.demo.networking.SynerzipAPI.Companion.ENDPOINT_WEATHER
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by SaurabhA on 04,October,2020
 */
interface WeatherService {

    @POST(ENDPOINT_WEATHER)
    fun getWeatherEndpoint(
        @Query("q") cityName: String,
        @Query("appid") appid: String = SynerzipAPI.getAppAPIKey()
    ): Observable<WeatherResponseBean>

}
