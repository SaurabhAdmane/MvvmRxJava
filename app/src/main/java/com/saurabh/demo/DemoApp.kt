package com.saurabh.demo

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.saurabh.demo.networking.SynerzipAPI
import java.lang.ref.WeakReference


/**
 * Created by SaurabhA on 03,October,2020
 */

class DemoApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        wApp!!.clear()
        wApp = WeakReference(this@DemoApp)
        initializeAppEnvironment()
    }

    private fun initializeAppEnvironment() {
        SynerzipAPI.setAPIEnvironment()
    }

    companion object {
        private var wApp: WeakReference<DemoApp>? = WeakReference<DemoApp>(null)
        val context: Context
            get() {
                val app =
                    if (null != wApp) wApp!!.get() else DemoApp()
                return if (app != null) app.applicationContext else DemoApp().applicationContext
            }
    }

}
