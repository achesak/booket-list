package com.chesak.adam.readinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Shows the settings
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the action bar details
        setTitle("Settings");
    }
}
