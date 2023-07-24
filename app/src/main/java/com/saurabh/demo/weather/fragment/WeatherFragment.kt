package com.saurabh.demo.weather.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.saurabh.demo.R
import com.saurabh.demo.base.BaseFragment
import com.saurabh.demo.utils.extension.showToast
import com.saurabh.demo.weather.viewmodels.WeatherViewModel
import com.saurabh.demo.databinding.FragmentWeatherBinding
import java.lang.reflect.Field

/**
 * Created by SaurabhA on 04,October,2020
 */
class WeatherFragment : BaseFragment() {

    lateinit var binding: FragmentWeatherBinding
    private val TAG = javaClass.name
    lateinit var searchToolbar: Toolbar
    private var searchMenu: Menu? = null
    private var itemSearch: MenuItem? = null
    lateinit var viewModel: WeatherViewModel

    override fun getRootLayout() = R.layout.fragment_weather

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun init(binding: ViewDataBinding) {
        this.binding = binding as FragmentWeatherBinding
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        viewModel = WeatherViewModel()
        binding.viewModel = viewModel
        setSearchToolbar()
        viewModel.hitAPI("NAgpur")
        //todo check old data, if present then delete
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(binding.toolbarSearch, 1, true, true);
                else
                    searchToolbar.visibility = View.VISIBLE;

                itemSearch!!.expandActionView();
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun setSearchToolbar() {
        searchToolbar = binding.toolbarSearch

        searchToolbar.inflateMenu(R.menu.menu_search)
        searchMenu = searchToolbar.getMenu()
        searchToolbar.setNavigationOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circleReveal(
                    binding.toolbarSearch,
                    1,
                    true,
                    false
                ) else
                searchToolbar.setVisibility(View.GONE)
        }
        itemSearch = searchMenu!!.findItem(R.id.action_filter_search)
        MenuItemCompat.setOnActionExpandListener(
            itemSearch,
            object : MenuItemCompat.OnActionExpandListener {
                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(binding.toolbarSearch, 1, true, false)
                    } else
                        searchToolbar.visibility = View.GONE
                    return true
                }

                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    return true
                }
            })
        initSearchView()
    }

    private fun initSearchView() {
        val searchView: SearchView =
            searchMenu!!.findItem(R.id.action_filter_search).actionView as SearchView

        // Enable/Disable Submit button in the keyboard
        searchView.isSubmitButtonEnabled = false

        // Change search close button image
        val closeButton: ImageView = searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setImageResource(R.drawable.ic_close)

        // set hint and the text colors
        val txtSearch =
            searchView.findViewById(R.id.search_src_text) as EditText
        txtSearch.hint = "Enter City Name"
        txtSearch.setHintTextColor(Color.DKGRAY)
        txtSearch.setTextColor(resources.getColor(R.color.colorPrimary))

        // set the cursor
        val searchTextView =
            searchView.findViewById(R.id.search_src_text) as AutoCompleteTextView
        try {
            val mCursorDrawableRes: Field =
                TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.setAccessible(true)
            mCursorDrawableRes.set(
                searchTextView,
                R.drawable.search_cursor
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            fun callSearch(query: String) {
                if (query.length > 3) {
                    showToast("Hit API")
                    viewModel.hitAPI(query)
                }
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun circleReveal(
        viewID: Toolbar,
        posFromRight: Int,
        containsOverflow: Boolean,
        isShow: Boolean
    ) {
        val myView: View = viewID
        var width = myView.width
        if (posFromRight > 0) width -= posFromRight * resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) - resources.getDimensionPixelSize(
            R.dimen.abc_action_button_min_width_material
        ) / 2
        if (containsOverflow) width -= resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
        val cx = width
        val cy = myView.height / 2
        val anim: Animator
        anim = if (isShow) ViewAnimationUtils.createCircularReveal(
            myView,
            cx,
            cy,
            0f,
            width.toFloat()
        ) else ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)
        anim.setDuration(220.toLong())

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            }
        })

        // make the view visible and start the animation
        if (isShow) myView.visibility = View.VISIBLE

        // start the animation
        anim.start()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): Fragment {
            return WeatherFragment().apply {
                arguments = bundle
            }
        }
    }
}
