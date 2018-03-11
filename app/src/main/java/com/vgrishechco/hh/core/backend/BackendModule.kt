package com.vgrishechco.hh.core.backend

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import com.vgrishechco.hh.core.backend.api.HeadHunterApi
import com.vgrishechco.hh.core.backend.network.HttpCache
import com.vgrishechco.hh.core.moshi.adapter.UriAdapter
import com.vgrishechco.hh.utils.AppInfo
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Singleton

@Module
class BackendModule {

    @Provides
    @Singleton
    fun provideApi(httpCaller: Call.Factory,
                   converterFactory: Converter.Factory): HeadHunterApi {

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://api.hh.ru")
                .callFactory(httpCaller)
                .validateEagerly(AppInfo.isDebugging())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)

        return retrofitBuilder.build().create(HeadHunterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpCaller(httpCache: Cache, httpInterceptors: ArrayList<Interceptor>): Call.Factory {
        val callerBuilder = OkHttpClient.Builder()

        callerBuilder.cache(httpCache)

        for (interceptor in httpInterceptors) {
            callerBuilder.addInterceptor(interceptor)
        }

        return callerBuilder.build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        val cacheFile = File(context.cacheDir, HttpCache.FILE_NAME)

        return Cache(cacheFile, HttpCache.FILE_SIZE)
    }

    @Provides
    @Singleton
    fun provideHttpInterceptors(httpLoggingInterceptor: HttpLoggingInterceptor): ArrayList<Interceptor> {
        return arrayListOf(httpLoggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        val builder = Moshi.Builder()

        builder.add(UriAdapter())
                .add(Date::class.java, Rfc3339DateJsonAdapter())

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggerInteceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message -> Timber.tag("API").d(message) }

        return HttpLoggingInterceptor(logger).apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}