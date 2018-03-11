package com.vgrishechco.hh.ui.base.adapter.epoxy

import android.support.annotation.CallSuper
import android.view.View
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyHolder

abstract class BaseEpoxyHolder : EpoxyHolder() {

    lateinit var itemView: View

    @CallSuper
    override fun bindView(itemView: View) {
        this.itemView = itemView
        ButterKnife.bind(this, itemView)
    }
}