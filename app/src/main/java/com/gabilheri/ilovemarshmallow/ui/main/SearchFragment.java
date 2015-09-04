package com.gabilheri.ilovemarshmallow.ui.main;

import android.os.Bundle;
import android.view.View;

import com.gabilheri.ilovemarshmallow.MarshmallowApp;
import com.gabilheri.ilovemarshmallow.MarshmallowUtils;
import com.gabilheri.ilovemarshmallow.base.BaseRecyclerListFragment;
import com.gabilheri.ilovemarshmallow.base.RxCallback;
import com.gabilheri.ilovemarshmallow.base.RxSubscriber;
import com.gabilheri.ilovemarshmallow.base.ViewItemCallback;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResult;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;

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
        implements ViewItemCallback, RxCallback<SearchResult>, OnScrolledCallback {

    static final String ITEMS_KEY = "search_results";

    SearchResultsAdapter mAdapter;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    String mCurrentSearchTerm;
    boolean mNewSearch = true;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<SearchResultItem> items = new ArrayList<>(0);
        mAdapter = new SearchResultsAdapter(items, this);
        initGridCardsList(mAdapter);
        mRecyclerView.addOnScrollListener(new SearchScrollListener(mGridLayoutManager, this));
        if(savedInstanceState != null) {
            items = Parcels.unwrap(savedInstanceState.getParcelable(ITEMS_KEY));
            mAdapter.refreshResults(items);
        }
    }

    public void search(String searchTherm, int page) {
        if(searchTherm.equals(mCurrentSearchTerm)) {
            mNewSearch = false;
        } else {
            mCurrentSearchTerm = searchTherm;
        }

        MarshmallowApp.instance().api().searchItems(searchTherm, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<>(this));
    }

    @Override
    public void onDataReady(SearchResult data) {
        if(mNewSearch) {
            mAdapter.refreshResults(data.getResults());
        } else {
            mNewSearch = true;
            mAdapter.addAll(data.getResults());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEMS_KEY, Parcels.wrap(mAdapter.getItems()));
    }

    @Override
    public void onScrolled(int page) {
        search(mCurrentSearchTerm, page);
    }

    @Override
    public void onItemClick(View v) {
        MarshmallowUtils.openProductDetail(getActivity(), v);
    }
}
