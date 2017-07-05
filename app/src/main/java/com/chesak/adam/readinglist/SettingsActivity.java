package com.chesak.adam.readinglist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.Locale;

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
        setTitle(R.string.title_settings);

        // Get the widgets
        final EditText pageRateText = (EditText) findViewById(R.id.settings_page_rate);
        final Switch rememberLastSearch = (Switch) findViewById(R.id.settings_search_remember);

        // Set the current values
        if (MainActivity.settings.pageRate > 0) {
            pageRateText.setText(String.format(Locale.US, "%d", MainActivity.settings.pageRate));
        }
        rememberLastSearch.setChecked(MainActivity.settings.rememberLastSearch);

        // Update page rate
        pageRateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pageRateInput = pageRateText.getText().toString();
                if (pageRateInput.equals("")) {
                    pageRateInput = "0";
                }
                MainActivity.settings.pageRate = Integer.parseInt(pageRateInput);
                MainActivity.io.saveSettings(SettingsActivity.this);
            }
        });

        // Clear data
        Button clearButton = (Button) findViewById(R.id.settings_data_clear_button);
        final RadioGroup clearGroup = (RadioGroup) findViewById(R.id.settings_data_group);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int selectedDataId = clearGroup.getCheckedRadioButtonId();
                String selectedDataName = "";
                switch (selectedDataId) {
                    case R.id.settings_data_reading:
                        selectedDataName = "reading";
                        break;
                    case R.id.settings_data_finished:
                        selectedDataName = "finished";
                        break;
                    case R.id.settings_data_all:
                        selectedDataName = "entire";
                        break;
                }

                // Confirm the delete
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.confirm_clear_data_title).setMessage(getString(R.string.confirm_clear_data, selectedDataName))
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        switch (selectedDataId) {
                                            case R.id.settings_data_reading:
                                                MainActivity.bookList.clearReading();
                                                break;
                                            case R.id.settings_data_finished:
                                                MainActivity.bookList.clearFinished();
                                                break;
                                            case R.id.settings_data_all:
                                                MainActivity.bookList.clearAll();
                                                break;
                                        }
                                    }
                                })
                        .setNegativeButton(R.string.dialog_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Nothing to do here
                                    }
                                })
                        .create().show();
            }
        });
    }
}
