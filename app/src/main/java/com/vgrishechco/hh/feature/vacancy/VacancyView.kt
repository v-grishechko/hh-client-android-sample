package com.vgrishechco.hh.feature.vacancy

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.core.moxy.AddToEndSingleByTagStrategy
import com.vgrishechco.hh.core.moxy.Tag

@StateStrategyType(SkipStrategy::class)
interface VacancyView: MvpView{

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showProgress()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showError()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = Tag.STATE)
    fun showData(vacancy: Vacancy)

}