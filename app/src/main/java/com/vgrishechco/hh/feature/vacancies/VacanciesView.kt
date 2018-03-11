package com.vgrishechco.hh.feature.vacancies

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.core.moxy.AddToEndSingleByTagStrategy
import com.vgrishechco.hh.core.moxy.Tag

@StateStrategyType(SkipStrategy::class)
interface VacanciesView: MvpView {

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showProgress()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showData()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(vacancies: List<Vacancy>)

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE_FOOTER)
    fun showFooterProgress()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE_FOOTER)
    fun showFooterError()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE_FOOTER)
    fun hideFooter()

    fun appendData(vacancies: List<Vacancy>)
}