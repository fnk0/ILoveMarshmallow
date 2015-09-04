package com.gabilheri.ilovemarshmallow.ui.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 */
public class SearchScrollListener extends RecyclerView.OnScrollListener {

    int previousTotal = 0; // The total number of items in the dataset after the last load
    boolean loading = true; // True if we are still waiting for the last set of data to load.
    int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int mCurrentPage = 1;
    private GridLayoutManager mGridLayoutManager;
    private OnScrolledCallback mCallback;

    public SearchScrollListener(GridLayoutManager mLinearLayoutManager, OnScrolledCallback mCallback) {
        this.mGridLayoutManager = mLinearLayoutManager;
        this.mCallback = mCallback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mGridLayoutManager.getItemCount();
        firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            mCurrentPage++;
            mCallback.onScrolled(mCurrentPage);
            loading = true;
        }
    }
}
