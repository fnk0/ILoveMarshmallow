package com.gabilheri.ilovemarshmallow.ui;

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

    int mPreviousTotal = 0; // The total number of items in the dataset after the last load
    boolean loading = true; // True if we are still waiting for the last set of data to load.
    int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int mFirstVisibleItem = 0;
    int mVisibleItemCount = 0;
    int mTotalItemCount = 0;
    private int mCurrentPage = 1;
    private GridLayoutManager mGridLayoutManager;
    private OnScrolledCallback mCallback;

    public SearchScrollListener(GridLayoutManager mLinearLayoutManager, OnScrolledCallback mCallback) {
        this.mGridLayoutManager = mLinearLayoutManager;
        this.mCallback = mCallback;
    }

    public void resetCount() {
        mCurrentPage = 1;
        mPreviousTotal = 0;
        mFirstVisibleItem = 0;
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.mTotalItemCount = totalItemCount;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mGridLayoutManager.getItemCount();
        mFirstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (mTotalItemCount > mPreviousTotal) {
                loading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }
        if (!loading && (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + visibleThreshold)) {
            mCurrentPage++;
            mCallback.onScrolled(mCurrentPage);
            loading = true;
        }
    }
}
