package com.vgrishechco.hh.utils

import com.vgrishechco.hh.BuildConfig

object AppInfo {
    fun isDebugging(): Boolean {
        return BuildConfig.DEBUG
    }
}