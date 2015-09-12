package com.gabilheri.ilovemarshmallow.ui.main;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.gabilheri.ilovemarshmallow.Const;
import com.gabilheri.ilovemarshmallow.MarshmallowUtils;
import com.gabilheri.ilovemarshmallow.base.BaseRecyclerListFragment;
import com.gabilheri.ilovemarshmallow.base.ItemCallback;
import com.gabilheri.ilovemarshmallow.data.DataContract;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class FavoritesFragment extends BaseRecyclerListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>, ItemCallback<View> {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    SearchResultsAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SearchResultsAdapter(null, this);
        mLoadingLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        initGridCardsList(mAdapter);
        if(savedInstanceState != null) {
            restartLoader();
        }
    }

    void restartLoader() {
        getActivity().getLoaderManager().restartLoader(Const.FAVORITES_LOADER, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(Const.FAVORITES_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == Const.FAVORITES_LOADER) {
            return new CursorLoader(getActivity(), DataContract.SearchResultEntry.CONTENT_URI, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<SearchResultItem> items = new ArrayList<>();
        while (data.moveToNext()) {
            items.add(SearchResultItem.fromCursor(data, false));
        }
        mAdapter.swapResults(items);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.reset();
    }

    @Override
    public void onItemClick(View v) {
        MarshmallowUtils.openProductDetail(getActivity(), v);
    }
}
