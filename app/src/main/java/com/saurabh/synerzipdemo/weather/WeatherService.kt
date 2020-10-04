package com.saurabh.synerzipdemo.weather

import com.saurabh.synerzipdemo.model.WeatherResponseBean
import com.saurabh.synerzipdemo.networking.SynerzipAPI
import com.saurabh.synerzipdemo.networking.SynerzipAPI.Companion.ENDPOINT_WEATHER
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
