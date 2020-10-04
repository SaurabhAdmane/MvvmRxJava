package com.saurabh.synerzipdemo

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.saurabh.synerzipdemo.networking.SynerzipAPI
import java.lang.ref.WeakReference


/**
 * Created by SaurabhA on 03,October,2020
 */

class SynerzipApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        wApp!!.clear()
        wApp = WeakReference(this@SynerzipApp)
        initializeAppEnvironment()
    }

    private fun initializeAppEnvironment() {
        SynerzipAPI.setAPIEnvironment()
    }

    companion object {
        private var wApp: WeakReference<SynerzipApp>? = WeakReference<SynerzipApp>(null)
        val context: Context
            get() {
                val app =
                    if (null != wApp) wApp!!.get() else SynerzipApp()
                return if (app != null) app.applicationContext else SynerzipApp().applicationContext
            }
    }

}
