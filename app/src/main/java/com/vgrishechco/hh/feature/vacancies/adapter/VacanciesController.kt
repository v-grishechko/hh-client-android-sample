package com.vgrishechco.hh.feature.vacancies.adapter

import android.view.View
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.vgrishechco.hh.core.backend.entity.Vacancy

class VacanciesController(private val listener: Listener) : EpoxyController() {

    private var isError: Boolean = false
    private var isProgress: Boolean = false

    private var vacancies: MutableList<Vacancy> = ArrayList()

    @AutoModel
    lateinit var progressModel: ProgressModel_

    @AutoModel
    lateinit var errorModel: ErrorModel_

    fun showVacancies(vacancies: List<Vacancy>) {
        this.vacancies = vacancies.toMutableList()
        requestModelBuild()
    }

    fun appendVacancies(vacancies: List<Vacancy>) {
        this.vacancies.addAll(vacancies)
        requestModelBuild()
    }

    fun showFooterProgress() {
        isError = false
        isProgress = true
        requestModelBuild()
    }

    fun showFooterError() {
        isError = true
        isProgress = false
        requestModelBuild()
    }

    fun hideFooter() {
        isError = false
        isProgress = false
        requestModelBuild()
    }

    override fun buildModels() {
        vacancies.forEach {
            vacancy {
                id(it.id)
                        .onVacancyClickListener(object : VacancyModel.OnVacancyClickListener {
                            override fun onVacancyClick(vacancy: Vacancy) {
                                listener.onVacancyClick(vacancy)
                            }

                        })
                vacancy(it)
            }
        }

        progressModel
                .addIf(isProgress, this)

        errorModel
                .onRetryClick(View.OnClickListener { listener.onFooterRetryClick() })
                .addIf(isError, this)
    }


    interface Listener {
        fun onVacancyClick(vacancy: Vacancy)

        fun onFooterRetryClick()
    }
}