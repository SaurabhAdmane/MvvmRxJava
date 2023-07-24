package com.saurabh.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.saurabh.demo.MainActivity
import com.saurabh.demo.utils.extension.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.saurabh.demo.R

/**
 * Created by SaurabhA on 03,October,2020
 *
 */

abstract class BaseFragment : Fragment() {

    private lateinit var binding: ViewDataBinding
    private var isViewInflated = false
    private var dialog: AppCompatDialog? = null
    private var progressDialog: AppCompatDialog? = null
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var baseViewModel: BaseViewModel? = null
    private var baseViewModelDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isViewInflated) {
            getRootView()
        } else {
            isViewInflated = true
            binding = DataBindingUtil.inflate(inflater, getRootLayout(), container, false)
            init(binding)
            subscribeBaseViewModelEvents()
            binding.root
        }
    }

    abstract fun getRootLayout(): Int

    abstract fun init(binding: ViewDataBinding)

    private fun subscribeBaseViewModelEvents() {
        //dialog visibility event to show or hide progress dialog and toast events
        baseViewModel?.let {
            compositeDisposable.add(baseViewModel!!.dialogVisibility.subscribe {
                if (it)
                    showProgressDialog()
                else hideProgressDialog()
            })

            compositeDisposable.add(baseViewModel!!.toast.subscribe {
                showToast(it)
            })
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            activity?.onAttachFragment(this)
            updateStatusBarColor()
        }
    }

    fun showProgressDialog(progressMsg: String? = null) {
        /* if (progressDialog == null) {
             progressDialog = CommonUtils.showLoadingDialog(activity)
         }
         try {
             progressDialog?.findViewById<TextView>(R.id.progress)?.text = progressMsg ?: ""
             if (!progressDialog?.isShowing!!)
                 progressDialog?.show()

         } catch (e: Exception) {
         }*/
    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun onResume() {
        super.onResume()
        if (context is MainActivity) {
            if (isVisible)
                updateStatusBarColor()
        }
    }

    /**
     *  Override this fun in fragment to get result
     */
    open fun afterFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {

    }

    override fun onDestroyView() {
        /**
         *  Removing all views from parent was getting IllegalStateException
         *  java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
         */

        view?.let {
            (view!!.parent as ViewGroup?)?.removeView(it)
        }
//        unbindView(view)
        dialog = null
        super.onDestroyView()
    }

    /**
     *  implement this function in your fragment to release objects and make objects for garbage collection
     */
    fun getRootView(): View? = binding.root

    open fun getStatusBarColor() = R.color.white

    open fun updateStatusBarColor() {
        if (activity != null && !activity!!.isFinishing && activity is MainActivity) {
            (activity as MainActivity).setStatusBarColor(getStatusBarColor())
        }
    }

    override fun onStart() {
        if (baseViewModel != null) {
            baseViewModelDisposable =
                baseViewModel!!.getDialogVisibility().subscribe { showDialog ->
                    if (null != dialog) {
                        if (showDialog!!)
                            dialog!!.show()
                        else if (dialog!!.isShowing)
                            dialog!!.dismiss()
                    }
                }
        }
        super.onStart()
    }


    override fun onDestroy() {
        super.onDestroy()
        baseViewModel?.dispose()
        if (null != baseViewModelDisposable && !baseViewModelDisposable!!.isDisposed) {
            baseViewModelDisposable!!.dispose()
        }

        if (baseViewModel != null) {
            baseViewModel!!.dispose()
            baseViewModel!!.getDialogVisibility().unsubscribeOn(AndroidSchedulers.mainThread())
            baseViewModel = null
        }
    }
}