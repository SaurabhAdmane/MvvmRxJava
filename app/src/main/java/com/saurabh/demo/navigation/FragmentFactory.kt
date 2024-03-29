package com.saurabh.demo.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saurabh.demo.weather.fragment.WeatherFragment

object FragmentFactory {

    val WEATHER = "WeatherFragment"

    fun create(fragmentId: String, bundle: Bundle): Fragment? {
        when (fragmentId) {

            WEATHER -> return WeatherFragment.newInstance(bundle)

            else -> return null
        }
    }
}
