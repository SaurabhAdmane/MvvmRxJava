package com.saurabh.demo.navigation

import android.app.Activity

interface ContextProvider {
    fun getActivityContext(): Activity
}