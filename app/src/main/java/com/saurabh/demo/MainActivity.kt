package com.saurabh.demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.saurabh.demo.navigation.ContextProvider
import com.saurabh.demo.navigation.FragmentFactory.WEATHER
import com.saurabh.demo.navigation.NavOption
import com.saurabh.demo.navigation.NavigationAnimation.Companion.NONE
import com.saurabh.demo.navigation.Router
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.Collections


/**
 * Created by SaurabhA on 03,October,2020
 */
class MainActivity : AppCompatActivity(), ContextProvider {

    var compositeDisposable = CompositeDisposable()
    var mAppAutoLockTimer: CountDownTimer? = null
    var uri: String = ""
    var mSessionTimeOut = false
    var mIsInBackground = false

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun checkSession(intent: Intent?) {
        val bundle = Bundle()
        Router.getInstance().navigate(WEATHER,
            bundle,
            NavOption()
                .apply {
                    navigationAnimation = NONE
                    clearBackStack = true
                })
        getIntent().data = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_main)
        Router.getInstance()
            .init(supportFragmentManager, R.id.navFragmentHost, this)

        checkSession(intent)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        sortByLastName();
    }

    fun getEmployeeList(): ArrayList<EmployeeData> {
        val list = ArrayList<EmployeeData>()
        val emp1 = EmployeeData("Xtz", "Yz", 9)
        list.add(emp1)
        val emp2 = EmployeeData("Paul", "Bzr", 4)
        list.add(emp2)
        val emp3 = EmployeeData("Saurabh", "Admane", 3)
        list.add(emp3)
        val emp4 = EmployeeData("Prerna", "Surbhi", 6)
        list.add(emp4)
        return list
    }

    fun sortByLastName() {
        val employeeList = getEmployeeList()

        Collections.sort(employeeList, Comparator { o1, o2 ->
            o1.lastName.compareTo(o2.lastName)
        })
        employeeList.forEach { it->
            Log.e("Sort : ", it.lastName)
        }
//-------------------------------------------------------------------------------------------------------------
        val comp = compareBy<EmployeeData>{ it.empId }
        employeeList.sortWith(comp)
        employeeList.forEach { it->
            Log.e("Emp : ", "${it.empId}")
        }

    }

    fun finishActivity() {
        Router.isLoggedIn = false
        intent = null
        finish()
    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        mSessionTimeOut = false
        mAppAutoLockTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        intent = null
        Router.getInstance().onDestroy()
        compositeDisposable.dispose()
        mAppAutoLockTimer?.cancel()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action == ACTION_VIEW) {
            if (Router.isLoggedIn) {
                if (mSessionTimeOut) {
                    uri = intent.data.toString()
                } else {
                    Router.getInstance()
                        .navigate(intent.data.toString(),
                            Bundle(),
                            NavOption()
                                .apply {
                                    navigationAnimation = NONE
                                })
                }
            } else {
                checkSession(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mIsInBackground = true
    }

    override fun onResume() {
        super.onResume()
        mSessionTimeOut = false
        mIsInBackground = false
        Router.getInstance().StartSessionTimer =
            true
        mAppAutoLockTimer?.cancel()
    }

    override fun getActivityContext(): Activity {
        return this
    }

    fun makeTransparentStatusBar(isTransparent: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isTransparent) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
    }
}
