package com.vgrishechco.hh.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ViewAnimator

class ViewDirector private constructor(private val activity: Activity?,
                                       private val fragment: Fragment?,
                                       private val viewContainer: View?,
                                       private val animatorId: Int) {

    fun show(@IdRes viewId: Int) {
        val animator = findView<ViewAnimator>(animatorId)
        val view = findView<View>(viewId)

        if (animator.displayedChild != animator.indexOfChild(view)) {
            animator.displayedChild = animator.indexOfChild(view)
        }
    }

    private fun <T : View> findView(viewId: Int): T {
        if (activity != null) {
            return activity.findViewById<View>(viewId) as T
        }

        if (fragment != null && fragment.view != null) {
            return fragment.view!!.findViewById<View>(viewId) as T
        }

        if (viewContainer != null) {
            return viewContainer.findViewById<View>(viewId) as T
        }

        throw IllegalStateException("You should provide valid activity, fragment or view.")
    }

    companion object {

        fun of(activity: Activity, @IdRes animatorId: Int): ViewDirector {
            return ViewDirector(activity, null, null, animatorId)
        }

        fun of(fragment: Fragment, @IdRes animatorId: Int): ViewDirector {
            return ViewDirector(null, fragment, null, animatorId)
        }

        fun of(viewContainer: View, @IdRes animatorId: Int): ViewDirector {
            return ViewDirector(null, null, viewContainer, animatorId)
        }
    }
}