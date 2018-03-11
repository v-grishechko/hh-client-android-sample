package com.vgrishechco.hh.core.backend.api

import com.vgrishechco.hh.core.backend.entity.Vacancies
import com.vgrishechco.hh.core.backend.entity.Vacancy
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeadHunterApi {

    @GET("/vacancies")
    fun getVacancies(@Query("page") page: Int,
                     @Query("per_page") perPage: Int): Single<Vacancies>

    @GET("/vacancies/{vacancy_id}")
    fun getVacancy(@Path("vacancy_id") id: Long): Single<Vacancy>

}