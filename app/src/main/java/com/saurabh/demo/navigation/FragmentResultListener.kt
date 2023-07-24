package com.saurabh.demo.navigation

import android.os.Bundle

interface FragmentResultListener {
    fun onFragmentResult(requestCode: Int, result: Int, data: Bundle)
}