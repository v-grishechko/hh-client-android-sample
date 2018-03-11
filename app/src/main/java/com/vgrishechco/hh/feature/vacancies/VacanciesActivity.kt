package com.vgrishechco.hh.feature.vacancies

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vgrishechco.hh.R
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.feature.vacancies.adapter.VacanciesController
import com.vgrishechco.hh.feature.vacancy.VacancyActivity
import com.vgrishechco.hh.ui.Paginator
import com.vgrishechco.hh.ui.base.BaseMvpActivity
import com.vgrishechco.hh.utils.ViewDirector
import dagger.android.AndroidInjection
import javax.inject.Inject

class VacanciesActivity : BaseMvpActivity(), VacanciesView, VacanciesController.Listener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.vacancies)
    lateinit var vacancies: RecyclerView

    @InjectPresenter
    @Inject
    lateinit var presenter: VacanciesPresenter

    private val vacanciesController: VacanciesController = VacanciesController(this)

    @ProvidePresenter
    fun providePresenter(): VacanciesPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancies)
        setUpToolbar()
        setUpRecycler()
    }

    override fun onInject() {
        super.onInject()
        AndroidInjection.inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @OnClick(R.id.button_retry)
    fun onButtonRetryClick() {
        presenter.doRetry()
    }

    override fun showProgress() {
        ViewDirector.of(this, R.id.animator).show(R.id.progress)
    }

    override fun showData() {
        ViewDirector.of(this, R.id.animator).show(R.id.vacancies)
    }

    override fun showError() {
        ViewDirector.of(this, R.id.animator).show(R.id.error)
    }

    override fun showFooterProgress() {
        vacanciesController.showFooterProgress()
    }

    override fun showFooterError() {
        vacanciesController.showFooterError()
    }

    override fun hideFooter() {
        vacanciesController.hideFooter()
    }

    override fun appendData(vacancies: List<Vacancy>) {
        vacanciesController.appendVacancies(vacancies)
    }

    override fun setData(vacancies: List<Vacancy>) {
        this.vacancies.adapter = vacanciesController.adapter
        vacanciesController.showVacancies(vacancies)
    }

    override fun onVacancyClick(vacancy: Vacancy) {
        startActivity(VacancyActivity.intent(this,
                vacancy.name,
                vacancy.id))
    }

    override fun onFooterRetryClick() {
        presenter.doFooterRetry()
    }

    private fun setUpRecycler() {
        vacanciesController.setFilterDuplicates(true)
        vacancies.layoutManager = LinearLayoutManager(this)
        Paginator.bind(vacancies, this.presenter)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
    }
}