package com.vgrishechco.hh

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class HhInstrumentationRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(classLoader: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(classLoader, HhMockApplication::class.java.name, context)
    }
}