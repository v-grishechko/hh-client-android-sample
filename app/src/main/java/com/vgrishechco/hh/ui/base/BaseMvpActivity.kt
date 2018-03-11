package com.vgrishechco.hh.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpDelegate

@SuppressLint("Registered")
open class BaseMvpActivity: AppCompatActivity() {

    private var mMvpDelegate: MvpDelegate<out BaseMvpActivity>? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject()
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }


    open fun onInject() {
        //no-op
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()

        getMvpDelegate().onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        getMvpDelegate().onDestroyView()

        if (isFinishing) {
            getMvpDelegate().onDestroy()
        }
    }

    /**
     * @return The [MvpDelegate] being used by this Activity.
     */
    fun getMvpDelegate(): MvpDelegate<out BaseMvpActivity> {
        if (mMvpDelegate == null) {
            mMvpDelegate = MvpDelegate(this)
        }
        return mMvpDelegate as MvpDelegate<out BaseMvpActivity>
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}