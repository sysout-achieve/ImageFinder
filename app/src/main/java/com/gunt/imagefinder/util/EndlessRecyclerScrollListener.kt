package com.gunt.imagefinder.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerScrollListener(private val endlessCnt: Int) :
    RecyclerView.OnScrollListener() {
    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (!loading && totalItemCount <= firstVisibleItem + endlessCnt) {
            onLoadMore()
            loading = true
        }
        if (firstVisibleItem + endlessCnt >= totalItemCount) {
            loading = false
        }
    }

    abstract fun onLoadMore()
}
