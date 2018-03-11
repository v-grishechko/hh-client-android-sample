package com.vgrishechco.hh.core.di.vacancy

import com.vgrishechco.hh.core.di.scope.PerActivity
import com.vgrishechco.hh.core.model.VacanciesModel
import com.vgrishechco.hh.core.rx.SchedulerProvider
import com.vgrishechco.hh.feature.vacancy.VacancyPresenter
import dagger.Module
import dagger.Provides

@Module
class VacancyModule {

    @Provides
    @PerActivity
    fun provideVacancyPresenter(vacanciesModel: VacanciesModel,
                                schedulers: SchedulerProvider): VacancyPresenter {
        return VacancyPresenter(vacanciesModel, schedulers)
    }
}