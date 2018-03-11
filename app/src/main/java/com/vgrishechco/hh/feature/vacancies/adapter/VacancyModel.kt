package com.vgrishechco.hh.feature.vacancies.adapter

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.vgrishechco.hh.R
import com.vgrishechco.hh.core.backend.entity.Vacancy
import com.vgrishechco.hh.core.formatter.VacancyFormatter
import com.vgrishechco.hh.ui.base.adapter.epoxy.BaseEpoxyHolder

@EpoxyModelClass(layout = R.layout.view_vacancy)
abstract class VacancyModel : EpoxyModelWithHolder<VacancyModel.VacancyViewHolder>() {

    @EpoxyAttribute
    lateinit var vacancy: Vacancy

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onVacancyClickListener: OnVacancyClickListener

    override fun bind(holder: VacancyViewHolder) {
        super.bind(holder)
        holder.name.text = vacancy.name
        holder.price.text = VacancyFormatter.formatSalary(holder.itemView.context, vacancy.salary)
        holder.vacancyContainer.setOnClickListener {
            onVacancyClickListener.onVacancyClick(vacancy)
        }
        holder.company.text = String.format("%s, %s", vacancy.company?.name, vacancy.area?.name)
    }

    class VacancyViewHolder : BaseEpoxyHolder() {

        @BindView(R.id.name)
        lateinit var name: TextView

        @BindView(R.id.price)
        lateinit var price: TextView

        @BindView(R.id.company)
        lateinit var company: TextView

        @BindView(R.id.date)
        lateinit var date: TextView

        @BindView(R.id.vacancyContainer)
        lateinit var vacancyContainer: View
    }

    interface OnVacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}