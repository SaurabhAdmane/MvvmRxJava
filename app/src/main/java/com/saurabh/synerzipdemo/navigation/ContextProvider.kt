package com.saurabh.synerzipdemo.navigation

import android.app.Activity

interface ContextProvider {
    fun getActivityContext(): Activity
}