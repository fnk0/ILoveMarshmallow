package com.gabilheri.ilovemarshmallow.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.BaseActivity;
import com.gabilheri.ilovemarshmallow.ui.FragmentAdapter;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @Bind(R.id.viewpager)
    ViewPager mPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.search_atv)
    AutoCompleteTextView mSearchTv;

    SearchFragment searchFragment;

    InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setStatusBarColor(R.color.primary_dark);
        setTitle("I Love Marshmallow");
        searchFragment = SearchFragment.newInstance();
        FragmentAdapter adapter = new FragmentAdapter(mFragmentManager);
        adapter.addFragment(searchFragment, "Search Results");
        adapter.addFragment(FavoritesFragment.newInstance(), "Favorites");
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
        mSearchTv.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchFragment.search(mSearchTv.getText().toString(), 1);
            mInputMethodManager.hideSoftInputFromWindow(mSearchTv.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            handled = true;
        }
        return handled;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
