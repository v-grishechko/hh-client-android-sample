package com.vgrishechco.hh.core.di

import com.vgrishechco.hh.core.di.scope.PerActivity
import com.vgrishechco.hh.core.di.vacancy.VacancyModule
import com.vgrishechco.hh.feature.vacancies.VacanciesActivity
import com.vgrishechco.hh.feature.vacancy.VacancyActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [VacanciesModule::class])
    abstract fun bindVacancies(): VacanciesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [VacancyModule::class])
    abstract fun bindVacancy(): VacancyActivity
}