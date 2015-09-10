package com.gabilheri.ilovemarshmallow.ui.main;

import android.os.Bundle;
import android.view.View;

import com.gabilheri.ilovemarshmallow.MarshmallowApp;
import com.gabilheri.ilovemarshmallow.MarshmallowUtils;
import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.BaseRecyclerListFragment;
import com.gabilheri.ilovemarshmallow.base.RxCallback;
import com.gabilheri.ilovemarshmallow.base.RxSubscriber;
import com.gabilheri.ilovemarshmallow.base.ViewItemCallback;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResult;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;
import com.gabilheri.ilovemarshmallow.ui.OnScrolledCallback;
import com.gabilheri.ilovemarshmallow.ui.Path;
import com.gabilheri.ilovemarshmallow.ui.SearchScrollListener;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class SearchFragment extends BaseRecyclerListFragment
        implements ViewItemCallback, RxCallback<SearchResult>, OnScrolledCallback, OnStateChangeListener {

    static final String ITEMS_KEY = "search_results";
    static final String NEW_SEARCH = "new_search";
    static final String CURRENT_TERM = "current_term";
    static final String CURRENT_POSITION = "current_position";
    static final String CURRENT_PAGE = "current_page";

    SearchResultsAdapter mAdapter;

    String mCurrentSearchTerm;
    boolean mNewSearch = true;
    SearchScrollListener mScrollListener;
    boolean isFirstOpen = false;

    int mCurrentPage = 1;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFillableLoader.setOnStateChangeListener(this);
        List<SearchResultItem> items = new ArrayList<>(0);
        mAdapter = new SearchResultsAdapter(items, this);
        initGridCardsList(mAdapter);
        mScrollListener = new SearchScrollListener(mGridLayoutManager, this);
        swapViews(false);

        if(savedInstanceState != null) {
            items = Parcels.unwrap(savedInstanceState.getParcelable(ITEMS_KEY));
            mCurrentSearchTerm = savedInstanceState.getString(CURRENT_TERM);
            mNewSearch = savedInstanceState.getBoolean(NEW_SEARCH);
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
            mAdapter.swapResults(items);
            if(items.size() > 0) {
                swapViews(true);
                mRecyclerView.smoothScrollToPosition(savedInstanceState.getInt(CURRENT_POSITION));
            }
        }

        mScrollListener.setCurrentPage(mCurrentPage);
        mScrollListener.setTotalItemCount(items.size());

        mRecyclerView.addOnScrollListener(mScrollListener);

        if (items.size() == 0) {
            isFirstOpen = true;
            loadPathIntoLoader(Path.MARSHMALLOW);
            mFillableLoader.start();
        }
    }

    public void search(String searchTherm, int page) {
        mCurrentPage = page;

        // If is not a new search we set a boolean
        // This will determine if we want to add items to the adapter
        // or reset the adapter itself
        if(searchTherm.equals(mCurrentSearchTerm)) {
            mNewSearch = false;
        } else {
            // Start the loading animation
            swapViews(false);
            mCurrentSearchTerm = searchTherm;
        }

        mEmptyText.setText(getString(R.string.loading));
        loadPathIntoLoader(Path.getRandomPath());
        mFillableLoader.start();

        MarshmallowApp.instance().api().searchItems(searchTherm, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<>(this));
    }

    @Override
    public void onDataReady(SearchResult data) {
        swapViews(true);
        if(mNewSearch) {
            mScrollListener.resetCount();
            mAdapter.swapResults(data.getResults());
        } else {
            mNewSearch = true;
            mAdapter.addAll(data.getResults());
        }
    }

    @Override
    public void onDataError(Throwable e) {
        mEmptyText.setText(getString(R.string.error_search));
        mFillableLoader.setSvgPath(Path.CLOUD);
        mFillableLoader.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ITEMS_KEY, Parcels.wrap(mAdapter.getmItems()));
        outState.putBoolean(NEW_SEARCH, mNewSearch);
        outState.putString(CURRENT_TERM, mCurrentSearchTerm);
        outState.putInt(CURRENT_POSITION, mGridLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(CURRENT_PAGE, mCurrentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onScrolled(int page) {
        search(mCurrentSearchTerm, page);
    }

    @Override
    public void onItemClick(View v) {
        MarshmallowUtils.openProductDetail(getActivity(), v);
    }

    @Override
    public void onStateChange(int state) {
        if (state == State.FINISHED) {
            if (isFirstOpen) {
                isFirstOpen = false;
                loadPathIntoLoader(Path.SEARCH);
                mEmptyText.setText(getString(R.string.empty_search));
                mFillableLoader.start();
            }
        }
    }
}
