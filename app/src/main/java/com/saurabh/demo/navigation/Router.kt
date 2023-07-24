package com.saurabh.demo.navigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics.Event.LOGIN
import com.saurabh.demo.DemoApp
import com.saurabh.demo.base.BaseFragment
import com.saurabh.demo.navigation.NavigationAnimation.Companion.RIGHT_TO_LEFT
import com.saurabh.demo.utils.extension.isValidString


class Router : FragmentManager.OnBackStackChangedListener {
    private var supportFragmentManager: FragmentManager? = null
    private var fragmentHost: Int = 0
    private var mFragmentResultListener: FragmentResultListener? = null
    var StartSessionTimer = true
    private var progressDialog: AppCompatDialog? = null
    private var contextProvider: ContextProvider? = null

    init {
        Router.Companion.instance = this
    }

    fun init(fragmentManager: FragmentManager, host: Int, contextProvider: ContextProvider) {
        supportFragmentManager = fragmentManager
        fragmentHost = host
        this.contextProvider = contextProvider
    }


    fun navigateForResult(
        path: String,
        mFragmentResultListener: FragmentResultListener,
        bundle: Bundle? = Bundle(),
        navOption: NavOption? = null
    ) {
        this.mFragmentResultListener = mFragmentResultListener
        navigate(path, bundle, navOption)
    }


    /**
     * if the given path long url or it is not url then proceed with redirect or
     * if the path is short url then call firebase sdk and get the long url and redirect
     */
    @JvmOverloads
    fun navigate(path: String, bundle: Bundle? = null, navOption: NavOption? = null) {
        //Check if the given path is short url or long
//        if (DeepLinkUri.isLongPath(path)) {
//            //if it is long url then get the parameters from url and redirect
//            val parameterMap = HashMap(DeepLinkUri.getParameterMap(path))
        performFragmentTransaction(path, bundle, navOption)
//        } else {
//            //if the given path short url then convert the url long by using firebase sdk and then redirect
//            showProgressDialog()
//            DeepLinkUri.getDynamicLinks(path, {
//                val linkData = it as PendingDynamicLinkData
//                if (linkData != null && linkData.link != null && isValidString(linkData.link.toString())) {
//                    hideProgressDialog()
//                    performFragmentTransaction(
//                        linkData.link.toString(),
//                        bundle,
//                        navOption,
//                        HashMap(DeepLinkUri.getParameterMap(linkData.link.toString()))
//                    )
//                }
//            }, {
//                hideProgressDialog()
//            })
//        }
    }

    private fun performFragmentTransaction(
        path: String,
        data: Bundle?,
        navOption: NavOption?
    ) {
        var data = data

        var addToBackStack = true
        var fragmentKey: String? = ""

        if (navOption != null) {
            addToBackStack = navOption.addToBackStack
            if (navOption.clearBackStack) {
                supportFragmentManager!!.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            } else {
                popUpTo(navOption.clearUpTO)
            }
        }

        try {
            if (data == null)
                data = Bundle()

//            //Adding parameters in bundle
//            for ((key, value) in parameterMap) {
//                data.putString(key, value)
//            }

            /**
             * Get Fragment from id
             * Priorities
             * 1. feature_id
             * 2. sub_feature_id
             * 3. sub_feature_detail_id
             *
             * else it can be direct fragment id without key (This case will come when  we redirect from inside the app)
             * */
//            if (containValidFeatureId(parameterMap, FEATURE_ID))
//                fragmentKey = parameterMap[FEATURE_ID]
//            else if (containValidFeatureId(parameterMap, SUB_FEATURE_ID))
//                fragmentKey = parameterMap[SUB_FEATURE_ID]
//            else if (containValidFeatureId(parameterMap, SUB_FEATURE_DETAIL_ID))
//                fragmentKey = parameterMap[SUB_FEATURE_DETAIL_ID]
//            else
            fragmentKey = path


        } catch (e: Exception) {
            Log.e(Router.Companion.TAG, e.localizedMessage)
        }

        val fragment: Fragment?

        if (isValidString(fragmentKey))
            fragment = FragmentFactory.create(
                fragmentKey!!,
                data!!
            )
        else
            return

        /**
         * If fragment factory does not have fragment for specified id then it will return run
         * */
        if (fragment == null)
            return

        val transaction = supportFragmentManager!!.beginTransaction()

        /**
         * Check navigation options
         * it may contain shared element transition for animation
         * or fragment transaction animation ie. left to right, right to left or bottom to top etc
         * */
        if (navOption != null) {
            if (navOption.view != null && ViewCompat.getTransitionName(navOption.view!!) != null) {
                transaction.addSharedElement(
                    navOption.view!!,
                    ViewCompat.getTransitionName(navOption.view!!)!!
                )
            } else {
                val animation =
                    NavigationAnimation.Companion.getAnimation(
                        navOption.navigationAnimation
                    )
                transaction.setCustomAnimations(
                    animation.enterAnim,
                    animation.exitAnim,
                    animation.popEnterAnim,
                    animation.popExitAnim
                )
            }
        } else {
            //Default animation right to left
            val animation =
                NavigationAnimation.Companion.getAnimation(
                    RIGHT_TO_LEFT
                )
            transaction.setCustomAnimations(
                animation.enterAnim,
                animation.exitAnim,
                animation.popEnterAnim,
                animation.popExitAnim
            )
        }

        //Add to backstack
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        //Replace fragment
        transaction.replace(fragmentHost, fragment, fragment.javaClass.simpleName)

        //commit fragment transaction
        transaction.commitAllowingStateLoss()
    }

    /**Remove fragments from backstaack
     * @param count no of fragments to remove from backstack
     * */
    private fun popUpTo(count: Int) {
        if (count > 0 && supportFragmentManager!!.backStackEntryCount > 0) {
            for (i in 0 until count) {
                try {
                    supportFragmentManager!!.fragments[supportFragmentManager!!.fragments.size - 1].onDestroy()
                    supportFragmentManager!!.popBackStackImmediate()
                } catch (e: Exception) {
                    Log.e(Router.Companion.TAG, e.localizedMessage)
                }

            }
        }
    }

    /**
     * Remove fragment from backstack and send the result to previous fragment
     * */
    @JvmOverloads
    fun setResultAndFinish(
        requestCode: Int,
        resultCode: Int,
        data: Bundle?,
        navOption: NavOption? = null
    ) {

        if (navOption != null)
            popUpTo(navOption.clearUpTO)
        else
            popUpTo(1)

        if (supportFragmentManager!!.fragments[supportFragmentManager!!.fragments.size - 1] is BaseFragment) {
            if (mFragmentResultListener != null) {
                mFragmentResultListener!!.onFragmentResult(
                    requestCode,
                    resultCode,
                    data ?: Bundle()
                )
                mFragmentResultListener = null
            } else if (supportFragmentManager!!.fragments[supportFragmentManager!!.fragments.size - 1] is FragmentResultListener) {
                (supportFragmentManager!!.fragments[supportFragmentManager!!.fragments.size - 1] as FragmentResultListener).onFragmentResult(
                    requestCode,
                    resultCode,
                    data ?: Bundle()
                )
            } else {
                (supportFragmentManager!!.fragments[supportFragmentManager!!.fragments.size - 1] as BaseFragment).afterFragmentResult(
                    requestCode,
                    resultCode,
                    data ?: Bundle()
                )
            }
        }
    }

    /**
     * @param steps no of fragments to remvoe from backstack
     * */
    fun goBack(steps: Int) {
        popUpTo(steps)
    }

    fun goBack(): Boolean {
        return popFragment()
    }

    fun popFragment(): Boolean {
        if (supportFragmentManager!!.fragments.size > 0) {
            try {
                popUpTo(1)
            } catch (e: ArrayIndexOutOfBoundsException) {
                return false
            }

            return true
        } else
            return false
    }

    /**
     * Logout use from app
     * */
    fun logout() {
        //unsubscribe fcm topics
//        val local = SessionManager.getSession().topics
//        unsubscribeFromFCMTopics(local)
        logout(-1, "")
    }

    fun logout(errorCode: Int, msg: String) {
        Router.Companion.isLoggedIn = false
        //Canceling all scheduled tasks
//        WorkManager.getInstance().cancelAllWorkByTag(WORKER_WITH_TOKEN)

        //Invaliding local session (Shared preference)
//        SessionManager.getSession().isFingerPrintAuthOn = false
//        SessionManager.getInstance().invalidateSession()

        //Removing all app shortcuts
//        TMWPayShortcutManager().removeAllShortcuts()

        //Clearing Glide cache
        Thread { Glide.get(DemoApp.context).clearDiskCache() }.start()

        //Navigating user to mobile no screen
        val navOption = NavOption()
        navOption.clearBackStack = true
        navigate(LOGIN, navOption = navOption)

        //Showing user user error popup if user is logged out from server side
        showErrorDialog(errorCode, msg)
    }

    /**
     * Show error popup
     * @param errorCode type of error of which user is logged out
     * @param msg error msg to show
     * */
    private fun showErrorDialog(errorCode: Int, msg: String) {
        if (errorCode >= 0) {
            var title = ""
            var desc = ""
            val negativeBtnText = ""
            var positiveBtnText = ""

            /*when (errorCode) {
                LOGGED_IN_ANOTHER_DEVICE -> {
                    title = getString(R.string.app_name)
                    desc =
                        if (isValidString(msg)) msg else getString(R.string.logged_in_another_device)
                    positiveBtnText = getString(R.string.ok)
                }

                LOGOUT_ERROR_CODE, HELP_AND_SUPPORT_ERROR_CODE, MPIN_BLOCK_ERROR_CODE -> {
                    title = getString(R.string.app_name)
                    desc = if (isValidString(msg)) msg else getString(R.string.oops)
                    positiveBtnText = getString(R.string.ok)
                    logoutEvent(errorCode)
                }
            }

            ConfirmationDialog()
                .setTitle(title)
                .setDescription(desc)
                .setNegativeButton(negativeBtnText)
                .setPositiveButton(positiveBtnText)
                .show(supportFragmentManager!!, ConfirmationDialog.TAG)*/
        }
    }

    override fun onBackStackChanged() {

    }

    /**
     * To show progress screen to user when we convert short dynamic link to long  using firebase sdk
     * */
//    internal fun showProgressDialog() {
//        if (progressDialog == null) {
//            progressDialog = CommonUtils.showLoadingDialog(contextProvider!!.getActivityContext())
//        }
//        if (!progressDialog!!.isShowing)
//            progressDialog!!.show()
//    }

    internal fun hideProgressDialog() {
        if (progressDialog != null)
            progressDialog!!.dismiss()
    }

    fun onDestroy() {
        progressDialog = null
        supportFragmentManager = null
        Router.Companion.instance = null
    }

    /**
     * Singleton instance of router class
     * user can get instance and redirect to other fragments
     * */
    companion object {
        private val TAG = "Router"
        internal var instance: Router? = null
        var URI = "deep_link_uri"
        var isLoggedIn = false

        fun getInstance(): Router {
            if (Router.Companion.instance == null)
                Router.Companion.instance =
                    Router()
            return Router.Companion.instance!!
        }
    }
}
