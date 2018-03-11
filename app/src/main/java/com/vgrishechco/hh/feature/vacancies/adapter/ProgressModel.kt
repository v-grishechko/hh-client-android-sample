package com.vgrishechco.hh.feature.vacancies.adapter

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.vgrishechco.hh.R
import com.vgrishechco.hh.ui.base.adapter.epoxy.BaseEpoxyHolder

@EpoxyModelClass(layout = R.layout.view_footer_progress)
abstract class ProgressModel: EpoxyModelWithHolder<ProgressModel.ProgressHolder>() {

    class ProgressHolder: BaseEpoxyHolder()
}