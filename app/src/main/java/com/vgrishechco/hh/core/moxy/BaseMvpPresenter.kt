package com.vgrishechco.hh.core.moxy

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseMvpPresenter<View : MvpView> : MvpPresenter<View>() {

    private val disposables = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable?) {
        if(disposable != null) {
            disposables.add(disposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}