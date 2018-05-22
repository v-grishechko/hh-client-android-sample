package com.vgrishechco.hh.core.backend.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class HttpHostInterceptor: Interceptor {

   private var url: HttpUrl? = null

   fun setHost(url: HttpUrl?) {
       this.url = url
   }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (url == null) {
            return chain.proceed(chain.request())
        }

        var request = chain.request()
        val url = this.url

        if (url != null) {
            val requestUrl = request.url().newBuilder()
                    .host(url.host())
                    .port(url.port())
                    .scheme(url.scheme())
                    .build()

            request = request.newBuilder()
                    .url(requestUrl)
                    .build()
        }

        return chain.proceed(request)
    }
}