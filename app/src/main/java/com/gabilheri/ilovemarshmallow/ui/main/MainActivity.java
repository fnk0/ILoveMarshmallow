package com.gabilheri.ilovemarshmallow.ui.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gabilheri.ilovemarshmallow.R;
import com.gabilheri.ilovemarshmallow.base.BaseActivity;
import com.gabilheri.ilovemarshmallow.ui.FragmentAdapter;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager mPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarColor(R.color.primary_dark);

        setTitle("I Love Marshmallow");
        FragmentAdapter adapter = new FragmentAdapter(mFragmentManager);
        adapter.addFragment(SearchFragment.newInstance(), "Search Results");
        adapter.addFragment(FavoritesFragment.newInstance(), "Favorites");
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
