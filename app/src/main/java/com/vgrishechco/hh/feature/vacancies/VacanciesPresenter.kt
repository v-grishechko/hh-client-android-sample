package com.vgrishechco.hh.feature.vacancies

import com.arellomobile.mvp.InjectViewState
import com.vgrishechco.hh.core.backend.BackendLimits
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.core.backend.network.Lce
import com.vgrishechco.hh.core.backend.network.toLce
import com.vgrishechco.hh.core.model.VacanciesModel
import com.vgrishechco.hh.core.moxy.BaseMvpPresenter
import com.vgrishechco.hh.core.rx.SchedulerProvider
import com.vgrishechco.hh.ui.Paginator
import io.reactivex.disposables.Disposable

@InjectViewState
class VacanciesPresenter(private val vacanciesModel: VacanciesModel,
                         private val schedulers: SchedulerProvider) : BaseMvpPresenter<VacanciesView>(),
        Paginator.Pager {

    private var currentPage = BackendLimits.PAGE_MINIMUM
    private var pages = 0
    private val vacancies: MutableList<Vacancy> = ArrayList()

    private var vacanciesObservable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUpVacancies()
    }

    override fun detachView(view: VacanciesView?) {
        super.detachView(view)
        viewState.setData(vacancies)
    }

    private fun setUpVacancies() {
        vacanciesObservable?.dispose()

        vacanciesObservable = vacanciesModel
                .vacancies()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .toLce()
                .subscribe {
                    when (it) {
                        is Lce.Error -> viewState.showError()
                        is Lce.Progress -> viewState.showProgress()
                        is Lce.Data -> {
                            currentPage = it.data.page
                            pages = it.data.pages
                            vacancies.clear()
                            vacancies.addAll(it.data.vacancies)
                            viewState.showData()
                            viewState.setData(it.data.vacancies.toList())
                        }
                    }
                }

        unsubscribeOnDestroy(vacanciesObservable)
    }

    fun doRetry() {
        setUpVacancies()
    }

    fun doFooterRetry() {
        loadVacancies(currentPage + 1)
    }

    private fun loadVacancies(page: Int) {
        vacanciesObservable?.dispose()

        vacanciesObservable = vacanciesModel
                .vacancies(page = page)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .toLce()
                .subscribe {
                    when (it) {
                        is Lce.Error -> viewState.showFooterError()
                        is Lce.Progress -> viewState.showFooterProgress()
                        is Lce.Data -> {
                            viewState.hideFooter()

                            if (it.data.found != null && it.data.found > 0) {
                                currentPage = it.data.page
                                pages = it.data.pages
                                vacancies.addAll(it.data.vacancies)
                                viewState.appendData(it.data.vacancies.toList())
                            }
                        }
                    }
                }

        unsubscribeOnDestroy(vacanciesObservable)
    }

    override fun onNextPage(page: Int) {
        loadVacancies(page)
    }

    override fun isLastPage(page: Int): Boolean = page == pages - 1

    override fun isLoadNextPage(): Boolean = vacanciesObservable?.isDisposed ?: true

    override fun currentPage(): Int = currentPage

}