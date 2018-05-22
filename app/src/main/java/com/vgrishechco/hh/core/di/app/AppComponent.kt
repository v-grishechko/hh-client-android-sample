package com.vgrishechco.hh.core.di.app

import android.app.Application
import com.vgrishechco.hh.App
import com.vgrishechco.hh.core.backend.BackendModule
import com.vgrishechco.hh.core.backend.interceptor.HttpHostInterceptor
import com.vgrishechco.hh.core.di.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton

@Component(modules = arrayOf(
        AppModule::class,
        BackendModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class
))
interface AppComponent {

    fun hostInterceptor(): HttpHostInterceptor

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}