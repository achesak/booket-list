package com.chesak.adam.readinglist.activity_main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chesak.adam.readinglist.activity_add.AddActivity;
import com.chesak.adam.readinglist.activity_progress.ProgressActivity;
import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_rating.RatingActivity;
import com.chesak.adam.readinglist.activity_detail.DetailActivity;
import com.chesak.adam.readinglist.activity_settings.SettingsActivity;
import com.chesak.adam.readinglist.data.BookData;
import com.chesak.adam.readinglist.data.BookList;
import com.chesak.adam.readinglist.data.Constants;
import com.chesak.adam.readinglist.data.SettingsData;
import com.chesak.adam.readinglist.shared.IO;

import java.util.HashMap;

/**
 * Main activity, shows the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class MainActivity extends AppCompatActivity {

    // Application data:
    public static IO io;
    public static SettingsData settings = new SettingsData();
    public static BookList bookList = new BookList();
    final public static HashMap<String, Object> imageCache = new HashMap<>();
    public static String lastSearchTitle = "";
    public static String lastSearchAuthor = "";

    // UI elements:
    public static BookListAdapter currentAdapter;
    public static BookListFinishedAdapter finishedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the interface
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (io == null) {
            io = new IO();
            bookList = io.readData(MainActivity.this);
            settings = io.readSettings(MainActivity.this);
        }

        io.saveData(MainActivity.this);

        // Set the action bar details
        setTitle(R.string.title_main);

        // Make the action bar look good with the tabs
        try {
            getSupportActionBar().setElevation(0);
        } catch (NullPointerException e) {
            // Do nothing
        }

        // Set up the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_tab_current));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_tab_finished));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_pager);
        final PagerAdapter adapter = new MainPagerAdapter
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

        // Show add screen
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addIntent);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        currentAdapter.notifyDataSetChanged();
        finishedAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_view_rating:
                Intent ratingIntent = new Intent(MainActivity.this, RatingActivity.class);
                startActivity(ratingIntent);
                return true;
            case R.id.action_view_progress:
                Intent progressIntent = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(progressIntent);
                return true;
            case R.id.action_view_random:
                BookData bookData = bookList.getRandomBook();
                if (bookData == null) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.action_view_random).setMessage(R.string.action_view_random_none)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                }
                            }).show();
                    return false;
                }
                Intent randomIntent = new Intent(MainActivity.this, DetailActivity.class);
                randomIntent.putExtra("book", bookData.getBook());
                randomIntent.putExtra("position", bookData.getIndex());
                randomIntent.putExtra("source", Constants.SOURCE_MAIN);
                startActivity(randomIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
