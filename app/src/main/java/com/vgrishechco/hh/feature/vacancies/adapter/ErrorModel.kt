package com.vgrishechco.hh.feature.vacancies.adapter

import android.view.View
import butterknife.BindView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.vgrishechco.hh.R
import com.vgrishechco.hh.ui.base.adapter.epoxy.BaseEpoxyHolder

@EpoxyModelClass(layout = R.layout.view_footer_error)
abstract class ErrorModel : EpoxyModelWithHolder<ErrorModel.ErrorViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onRetryClick: View.OnClickListener

    override fun bind(view: ErrorViewHolder) {
        super.bind(view)
        view.retry.setOnClickListener(onRetryClick)
    }

    class ErrorViewHolder : BaseEpoxyHolder() {
        @BindView(R.id.retry)
        lateinit var retry: View
    }
}