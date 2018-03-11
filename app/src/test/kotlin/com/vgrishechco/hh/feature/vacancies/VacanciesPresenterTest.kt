package com.vgrishechco.hh.feature.vacancies

import com.nhaarman.mockito_kotlin.*
import com.vgrishechco.hh.core.backend.entity.Vacancies
import com.vgrishechco.hh.core.model.VacanciesModel
import com.vgrishechco.hh.core.rx.SchedulerProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import test.ImmediateSchedulerProvider

class VacanciesPresenterTest {


    private lateinit var presenter: VacanciesPresenter
    private lateinit var view: VacanciesView

    private lateinit var vacanciesModel: VacanciesModel
    private val schedulers: SchedulerProvider = ImmediateSchedulerProvider()

    @Before
    fun beforeEachTest() {
        vacanciesModel = mock()
        view = mock()

        presenter = VacanciesPresenter(vacanciesModel, schedulers)
    }

    @Test
    fun `when success load vacancies, should show vacancies`() {
        returnVacancies()

        presenter.attachView(view)

        verify(view, times(1)).showProgress()
        verify(view, times(1)).showData()
    }

    @Test
    fun `when error load vacancies, should show error`() {
        returnVacancies(isError = true)

        presenter.attachView(view)

        verify(view, times(1)).showProgress()
        verify(view, times(1)).showError()
    }

    @Test
    fun `when success load next page of vacancies, should append vacancies`() {
        returnVacancies()

        presenter.attachView(view)
        presenter.onNextPage(1)

        verify(view, times(1)).showFooterProgress()
        verify(view, times(1)).appendData(any())
    }

    @Test
    fun `when error load next page of vacancies, should show error footer`() {
        returnVacancies()

        presenter.attachView(view)

        returnVacancies(isError = true)

        verify(view, times(1)).showFooterProgress()
        verify(view, times(1)).showFooterError()
    }

    private fun returnVacancies(isError: Boolean = false) {

        if (isError) {
            whenever(vacanciesModel.vacancies(any(), any())).thenReturn(Single.error(RuntimeException()))
        } else {
            val vacancies: Vacancies = mock()
            whenever(vacancies.found).thenReturn(1)
            whenever(vacanciesModel.vacancies(any(), any())).thenReturn(Single.just(vacancies))
        }
    }
}