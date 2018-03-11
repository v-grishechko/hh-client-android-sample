package com.vgrishechco.hh.core.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SchedulerProvider @Inject constructor() {

    open fun io() = Schedulers.io()

    open fun ui() = AndroidSchedulers.mainThread()
}