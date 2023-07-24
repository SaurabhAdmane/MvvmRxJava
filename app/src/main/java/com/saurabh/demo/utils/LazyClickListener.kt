package com.saurabh.demo.utils

import android.os.SystemClock
import android.view.View


/**
 * Created by SaurabhA on 03,October,2020
 */
abstract class LazyClickListener @JvmOverloads constructor(private var defaultInterval: Int = TWO_SECOND) :
    View.OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        performClick(v)
    }

    abstract fun performClick(v: View)

    companion object {
        val ONE_SECOND = 1000
        val TWO_SECOND = 2000
        val HALF_SECOND = 500
    }
}