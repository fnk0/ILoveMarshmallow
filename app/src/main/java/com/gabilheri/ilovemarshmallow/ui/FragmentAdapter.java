package com.gabilheri.ilovemarshmallow.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 8/16/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final SparseArray<Fragment> mFragments = new SparseArray<>();
    private final SparseArray<String> mFragmentTitles = new SparseArray<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.put(mFragments.size(), fragment);
        mFragmentTitles.put(mFragmentTitles.size(), title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

}
