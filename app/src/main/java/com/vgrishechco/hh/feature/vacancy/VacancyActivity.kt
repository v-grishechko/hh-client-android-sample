package com.vgrishechco.hh.feature.vacancy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vgrishechco.hh.R
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.core.formatter.VacancyFormatter
import com.vgrishechco.hh.core.glide.GlideApp
import com.vgrishechco.hh.ui.base.BaseMvpActivity
import com.vgrishechco.hh.utils.ViewDirector
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlin.math.log

class VacancyActivity : BaseMvpActivity(), VacancyView {

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.salary)
    lateinit var salary: TextView

    @BindView(R.id.date)
    lateinit var date: TextView

    @BindView(R.id.company)
    lateinit var company: TextView

    @BindView(R.id.logo)
    lateinit var logo: ImageView

    @BindView(R.id.description)
    lateinit var description: TextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @InjectPresenter
    @Inject
    lateinit var presenter: VacancyPresenter

    @ProvidePresenter
    fun providePresenter(): VacancyPresenter {
        presenter.setUp(vacancyId())
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy)
        setUpToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    @OnClick(R.id.button_retry)
    fun onRetryClick() {
        presenter.doRetry()
    }

    override fun onInject() {
        super.onInject()
        AndroidInjection.inject(this)
    }

    override fun showProgress() {
        ViewDirector.of(this, R.id.animator).show(R.id.progress)
    }

    override fun showError() {
        ViewDirector.of(this, R.id.animator).show(R.id.error)
    }

    override fun showData(vacancy: Vacancy) {
        ViewDirector.of(this, R.id.animator).show(R.id.vacancyContainer)
        setUpVacancy(vacancy)
    }

    private fun setUpVacancy(vacancy: Vacancy) {
        this.name.text = vacancy.name
        this.salary.text = VacancyFormatter.formatSalary(this, vacancy.salary)
        this.date.text = VacancyFormatter.formatDate(vacancy.publishedAt)
        this.company.text = vacancy.company?.name
        this.description.text = VacancyFormatter.formateDescription(vacancy.description)

        GlideApp.with(this)
                .load(vacancy.company?.logoUrls?.original)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(logo)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = vacancyName()
    }

    private fun vacancyId(): Long {
        return intent.getLongExtra(KEY_VACANCY_ID, 0)
    }

    private fun vacancyName(): String {
        return intent.getStringExtra(KEY_VACANCY_NAME)
    }

    companion object {
        private const val KEY_VACANCY_ID = "KEY_VACANCY_ID"
        private const val KEY_VACANCY_NAME = "KEY_VACANCY_NAME"

        fun intent(context: Context, vacancyName: String?, vacancyId: Long): Intent {
            return Intent(context, VacancyActivity::class.java)
                    .apply {
                        putExtra(KEY_VACANCY_ID, vacancyId)
                        putExtra(KEY_VACANCY_NAME, vacancyName)
                    }
        }
    }
}