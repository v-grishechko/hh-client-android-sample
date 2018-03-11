package com.vgrishechco.hh.core.di

import com.vgrishechco.hh.core.di.scope.PerActivity
import com.vgrishechco.hh.core.model.VacanciesModel
import com.vgrishechco.hh.core.rx.SchedulerProvider
import com.vgrishechco.hh.feature.vacancies.VacanciesPresenter
import dagger.Module
import dagger.Provides

@Module
class VacanciesModule {

    @PerActivity
    @Provides
    fun provideVacanciesPresenter(vacanciesModel: VacanciesModel,
                                  schedulers: SchedulerProvider): VacanciesPresenter {

        return VacanciesPresenter(vacanciesModel, schedulers)
    }
}