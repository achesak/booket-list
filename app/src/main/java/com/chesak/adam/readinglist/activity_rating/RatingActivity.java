package com.chesak.adam.readinglist.activity_rating;

import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.chesak.adam.readinglist.R;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the title
        setTitle(R.string.title_rating);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Make the action bar look good with the tabs
        try {
            getSupportActionBar().setElevation(0);
        } catch (NullPointerException e) {
            // Do nothing
        }

        // Set up the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.rating_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.rating_tab_highest));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.rating_tab_lowest));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.rating_pager);
        final PagerAdapter adapter = new RatingPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
