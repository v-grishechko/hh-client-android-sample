package com.vgrishechco.hh.core.model

import com.vgrishechco.hh.core.backend.BackendLimits
import com.vgrishechco.hh.core.backend.api.HeadHunterApi
import com.vgrishechco.hh.core.backend.entity.Vacancies
import com.vgrishechco.hh.core.backend.entity.Vacancy
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacanciesModel @Inject constructor(private val api: HeadHunterApi) {

    fun vacancies(page: Int = BackendLimits.PAGE_MINIMUM,
                  perPage: Int = BackendLimits.PER_PAGE): Single<Vacancies> {
        return api.getVacancies(page, perPage)
    }

    fun vacancy(id: Long): Single<Vacancy> {
        return api.getVacancy(id)
    }
}