package com.gabilheri.ilovemarshmallow.ui.main;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.gabilheri.ilovemarshmallow.Const;
import com.gabilheri.ilovemarshmallow.MarshmallowUtils;
import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.BaseActivity;
import com.gabilheri.ilovemarshmallow.data.DataContract;
import com.gabilheri.ilovemarshmallow.ui.FragmentAdapter;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements TextView.OnEditorActionListener,
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    @Bind(R.id.viewpager)
    ViewPager mPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.search_atv)
    AutoCompleteTextView mSearchTv;

    SearchFragment mSearchFragment;
    FavoritesFragment mFavoritesFragment;

    InputMethodManager mInputMethodManager;

    ArrayAdapter<String> mAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(Const.AUTO_COMPLETE_LOADER, null, this);
        setStatusBarColor(R.color.primary_dark);

        // Search bar stuff
        mAutoCompleteAdapter = new ArrayAdapter<>(this, R.layout.tv_autocomplete);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSearchTv.setOnEditorActionListener(this);
        mSearchTv.setAdapter(mAutoCompleteAdapter);

        // Initialize the fragments and the view pager adapter
        FragmentAdapter adapter = new FragmentAdapter(mFragmentManager);

        mSearchFragment = (SearchFragment) mFragmentManager.findFragmentByTag(adapter.getFragmentTag(R.id.viewpager, 0));
        mFavoritesFragment = (FavoritesFragment) mFragmentManager.findFragmentByTag(adapter.getFragmentTag(R.id.viewpager, 1));

        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance();
            mFavoritesFragment = FavoritesFragment.newInstance();
        }

        adapter.addFragment(mSearchFragment, getString(R.string.search_results));
        adapter.addFragment(mFavoritesFragment, getString(R.string.favorites));
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
        mSearchTv.setOnItemClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search(mSearchTv.getText().toString());
            handled = true;
        }
        return handled;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        search((String) parent.getAdapter().getItem(position));
    }

    /**
     * Convenience method to deal with common use cases when we search the API
     * Common cases include: dismiss the keyboard, dismiss auto complete dropdown and insert the searched term
     * into the database for future auto completions
     *
     * @param searchTerm
     *      The term to  be searched
     */
    private void search(String searchTerm) {
        if (mPager.getCurrentItem() != 0) {
            mPager.setCurrentItem(0, true);
        }

        getContentResolver().insert(DataContract.AutoCompleteEntry.CONTENT_URI, MarshmallowUtils.getAutoCompleteContentValues(searchTerm));
        mSearchFragment.search(searchTerm, 1);
        mInputMethodManager.hideSoftInputFromWindow(mSearchTv.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        if(mSearchTv.isPopupShowing()) {
            mSearchTv.dismissDropDown();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Const.AUTO_COMPLETE_LOADER) {
            return new CursorLoader(this, DataContract.AutoCompleteEntry.CONTENT_URI, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            mAutoCompleteAdapter.clear();
            while (data.moveToNext()) {
                mAutoCompleteAdapter.add(data.getString(data.getColumnIndex(DataContract.AutoCompleteEntry.SEARCH_TERM)));
            }
            mAutoCompleteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAutoCompleteAdapter.clear();
    }
}
