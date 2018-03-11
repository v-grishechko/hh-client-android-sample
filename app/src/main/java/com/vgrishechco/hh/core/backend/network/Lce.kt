package com.vgrishechco.hh.core.backend.network

import io.reactivex.Observable
import io.reactivex.Single

sealed class Lce<T>(val klass: Class<T>?) {

    class Data<T>(val data: T, clazz: Class<T>) : Lce<T>(clazz)

    class Error<T>(val error: Throwable, clazz: Class<T>) : Lce<T>(clazz)

    class Progress<T>(clazz: Class<T>) : Lce<T>(clazz)

    inline fun <reified T> typeOf(): Boolean = this.klass == T::class.java

    companion object {
        inline fun <reified T> errorOf(error: Throwable): Error<T> =
                Error(error, T::class.java)

        inline fun <reified T> dataOf(data: T) = Data(data, T::class.java)

        inline fun <reified T> progressOf() = Progress(T::class.java)
    }
}

inline fun <reified T> Observable<T>.toLce(): Observable<out Lce<T>> {
    return map<Lce<T>> { Lce.Data(it, T::class.java) }
            .onErrorReturn { Lce.Error(it, T::class.java) }
            .startWith(Lce.Progress(T::class.java))
}

inline fun <reified T> Single<T>.toLce(): Observable<out Lce<T>> {
    return toObservable()
            .map<Lce<T>> { Lce.Data(it, T::class.java) }
            .onErrorReturn { Lce.Error(it, T::class.java) }
            .startWith(Lce.Progress(T::class.java))
}