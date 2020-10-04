package com.saurabh.synerzipdemo.navigation

import com.saurabh.synerzipdemo.R

class NavigationAnimation {
    var enterAnim = 0
    var exitAnim = 0
    var popEnterAnim = 0
    var popExitAnim = 0


    companion object {
        const val LEFT_TO_RIGHT = 1
        const val RIGHT_TO_LEFT = 2
        const val BOTTOM_TO_TOP = 4
        const val DEFAULT = 3
        const val NONE = -1


        fun getAnimation(type: Int): NavigationAnimation {

            return when (type) {
                NavigationAnimation.Companion.LEFT_TO_RIGHT -> {
                    NavigationAnimation().apply {
                        enterAnim = R.anim.slide_left_to_right
                        exitAnim = R.anim.slide_left_to_right_exit
                        popExitAnim = R.anim.slide_right_to_left
                        popEnterAnim = R.anim.slide_right_to_left_enter
                    }
                }

                NavigationAnimation.Companion.BOTTOM_TO_TOP -> {
                    NavigationAnimation().apply {
                        enterAnim = R.anim.slide_bottom_to_top
                        exitAnim = R.anim.slide_top_to_bottom
                        popExitAnim = R.anim.no_animation
                        popEnterAnim = R.anim.no_animation
                    }
                }

                NavigationAnimation.Companion.NONE -> {
                    NavigationAnimation().apply {
                        enterAnim = R.anim.no_animation
                        exitAnim = R.anim.no_animation
                        popExitAnim = R.anim.no_animation
                        popEnterAnim = R.anim.no_animation
                    }
                }

                NavigationAnimation.Companion.DEFAULT, NavigationAnimation.Companion.RIGHT_TO_LEFT -> {
                    NavigationAnimation().apply {
                        enterAnim = R.anim.slide_right_to_left_enter
                        exitAnim = R.anim.slide_right_to_left
                        popExitAnim = R.anim.slide_left_to_right_exit
                        popEnterAnim = R.anim.slide_left_to_right
                    }
                }

                else -> {
                    NavigationAnimation()
                }
            }
        }
    }
}