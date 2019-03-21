package br.com.bancopanchallenge.view.custom

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    /**
     * The total number of items in the dataset after the last load
     */
    internal var mPreviousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val lastVisibleItem = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        val visibleThreshold = 1

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }

        if (!mLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            onLoadMore()
            mLoading = true
        }


    }

    abstract fun onLoadMore()

    fun clearPreviousTotal() {
        mPreviousTotal = 0
    }
}
