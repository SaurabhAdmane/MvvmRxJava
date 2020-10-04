package com.saurabh.synerzipdemo.base

import androidx.databinding.BaseObservable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
abstract class BaseViewModel : BaseObservable() {

    var dialogVisibility = PublishSubject.create<Boolean>()
    var toast = PublishSubject.create<String>()

    var dialogLabel = PublishSubject.create<String>()

    abstract fun dispose()

    fun getDialogVisibility(): Observable<Boolean> {
        return dialogVisibility.hide()
    }

    fun getDialogLabel(): Observable<String> {
        return dialogLabel.hide()
    }

}