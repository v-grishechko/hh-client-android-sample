package com.vgrishechco.hh

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDex
import com.vgrishechco.hh.core.di.app.AppComponent
import com.vgrishechco.hh.core.di.app.DaggerAppComponent
import com.vgrishechco.hh.utils.AppInfo
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class App: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setUpMultidex()
        setUpTimber()
        setUpComponent()
    }

    fun setUpMultidex() {
        MultiDex.install(this)
    }

    private fun setUpComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)
    }

    private fun setUpTimber() {
        if(AppInfo.isDebugging()) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}