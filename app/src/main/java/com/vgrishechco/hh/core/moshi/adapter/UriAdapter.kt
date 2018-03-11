package com.vgrishechco.hh.core.moshi.adapter

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class UriAdapter {

    @ToJson
    fun toJson(url: Uri): String = url.toString()

    @FromJson
    fun fromJson(url: String): Uri = Uri.parse(url)
}