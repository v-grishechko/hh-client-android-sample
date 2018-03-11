package com.vgrishechco.hh.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

open class Paginator private constructor(recyclerView: RecyclerView, private val listener: Pager) : RecyclerView.OnScrollListener() {
    private val linearLayoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

    interface Pager {
        fun onNextPage(page: Int)

        fun isLastPage(page: Int): Boolean

        fun isLoadNextPage(): Boolean

        fun currentPage(): Int
    }

    override fun onScrolled(recyclerView: android.support.v7.widget.RecyclerView?, horizontalScrollPosition: Int, verticalScrollPosition: Int) {
        super.onScrolled(recyclerView, horizontalScrollPosition, verticalScrollPosition)

        if (isVisibleLastItems() && listener.isLoadNextPage()) {
            listener.onNextPage(listener.currentPage() + 1)

            if (listener.isLastPage(listener.currentPage())) {
                recyclerView!!.removeOnScrollListener(this)
            }
        }
    }

    private fun isVisibleLastItems(): Boolean {
        val currentTotalItemsCount = linearLayoutManager.itemCount
        val visibleItemsCount = linearLayoutManager.childCount
        val visibleItemsPosition = linearLayoutManager.findFirstVisibleItemPosition()

        return currentTotalItemsCount - visibleItemsCount <= visibleItemsPosition + SCROLL_OFFSET_ITEMS_COUNT
    }

    companion object {
        private val SCROLL_OFFSET_ITEMS_COUNT = 1

        fun bind(recyclerView: RecyclerView, listener: Pager): Paginator {
            if (recyclerView.layoutManager is LinearLayoutManager) {
                val easyPaginator = Paginator(recyclerView, listener)
                recyclerView.addOnScrollListener(easyPaginator)
                return easyPaginator
            } else {
                throw IllegalStateException("Recyclerview must have " + LinearLayoutManager::class.java.simpleName + " not " + recyclerView.layoutManager.javaClass.simpleName)
            }
        }

        fun unbind(recyclerView: RecyclerView, paginator: Paginator?) {
            recyclerView.removeOnScrollListener(paginator)
        }
    }
}