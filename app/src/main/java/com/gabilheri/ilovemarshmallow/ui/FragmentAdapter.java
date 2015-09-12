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

    /**
     * Adds a fragment to this Adapter with it's title
     * @param fragment
     *      The fragment to be added
     * @param title
     *      The title of this fragment that will be shown in the Tabs
     */
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

    /**
     * Gets the Tag assigned by Android to the Fragment. Useful to use findFragmentByTag
     *
     * @param viewPagerId
     *      The ID of this ViewPager
     * @param fragmentPosition
     *      The position of the fragment
     * @return
     *      The Tag associated with the fragment at the current position
     */
    public String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }
}
