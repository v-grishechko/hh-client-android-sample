package com.vgrishechco.hh.feature.vacancy

import com.arellomobile.mvp.InjectViewState
import com.vgrishechco.hh.core.backend.network.Lce
import com.vgrishechco.hh.core.backend.network.toLce
import com.vgrishechco.hh.core.model.VacanciesModel
import com.vgrishechco.hh.core.moxy.BaseMvpPresenter
import com.vgrishechco.hh.core.rx.SchedulerProvider
import io.reactivex.disposables.Disposable

@InjectViewState
class VacancyPresenter(private val vacanciesModel: VacanciesModel,
                       private val schedulers: SchedulerProvider) : BaseMvpPresenter<VacancyView>() {

    private var vacancyId: Long = 0

    private var vacancyDisposable: Disposable? = null

    fun setUp(vacancyId: Long) {
        this.vacancyId = vacancyId
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUpVacancy()
    }

    private fun setUpVacancy() {
        vacancyDisposable?.dispose()

        vacancyDisposable = vacanciesModel.vacancy(vacancyId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .toLce()
                .subscribe{
                    when(it) {
                        is Lce.Progress -> viewState.showProgress()
                        is Lce.Error -> viewState.showError()
                        is Lce.Data -> viewState.showData(it.data)
                    }
                }

        unsubscribeOnDestroy(vacancyDisposable)
    }

    fun doRetry() {
        setUpVacancy()
    }
}