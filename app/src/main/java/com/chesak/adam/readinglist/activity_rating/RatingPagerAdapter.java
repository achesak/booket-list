package com.chesak.adam.readinglist.activity_rating;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Ratings view pager
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class RatingPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public RatingPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RatingTabHighest();
            case 1:
                return new RatingTabLowest();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}