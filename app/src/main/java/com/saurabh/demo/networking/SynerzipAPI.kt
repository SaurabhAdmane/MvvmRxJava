package com.saurabh.demo.networking

import com.saurabh.demo.BuildConfig


/**
 * Created by SaurabhA on 03,October,2020
 *
 */
class SynerzipAPI {
    companion object {
        const val ENDPOINT_WEATHER = "/data/2.5/weather?"

        var EnableLog = false
        private var APP_API_KEY = "6a9584e015eaca3ed72be15f28ead552"
        @JvmStatic
        fun getAppAPIKey(): String {
            return APP_API_KEY
        }

        private var BASE_ENDPOINT = ""
        @JvmStatic
        fun getBaseUrl(): String {
            return BASE_ENDPOINT
        }

        fun setAPIEnvironment() {
            when (APIEnvironment.valueOf(BuildConfig.ENV)) {
                APIEnvironment.DEBUG -> {
                    setupDebugEnvironment()
                }
                APIEnvironment.RELEASE -> {
                    setupProductionEnvironment()
                }
            }
        }

        private fun setupProductionEnvironment() {
            BASE_ENDPOINT = "http://api.openweathermap.org"
            APP_API_KEY = "6a9584e015eaca3ed72be15f28ead552"
            EnableLog = false
        }

        private fun setupDebugEnvironment() {
            BASE_ENDPOINT = "http://api.openweathermap.org"
            APP_API_KEY = "6a9584e015eaca3ed72be15f28ead552"
            EnableLog = true
        }
    }

    enum class APIEnvironment {
        DEBUG, RELEASE
    }
}
