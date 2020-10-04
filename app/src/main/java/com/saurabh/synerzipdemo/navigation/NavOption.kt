package com.saurabh.synerzipdemo.navigation

import android.view.View

class NavOption {
    var clearUpTO = 0
    var clearBackStack = false
    var addToBackStack = true
    var view: View? = null
    var navigationAnimation =
        NavigationAnimation.Companion.RIGHT_TO_LEFT
}