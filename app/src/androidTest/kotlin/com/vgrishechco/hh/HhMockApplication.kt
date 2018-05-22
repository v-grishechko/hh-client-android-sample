package com.vgrishechco.hh

import okhttp3.HttpUrl

class HhMockApplication: App() {

    override fun onCreate() {
        super.onCreate()
        interceptMockServer()
    }

    fun interceptMockServer() {
        appComponent.hostInterceptor().setHost(HttpUrl.parse("http://127.0.0.1:4512"))
    }
}