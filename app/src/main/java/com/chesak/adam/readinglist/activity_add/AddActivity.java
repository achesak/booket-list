package com.chesak.adam.readinglist.activity_add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chesak.adam.readinglist.activity_add_manual.AddManualActivity;
import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_search.SearchActivity;
import com.chesak.adam.readinglist.activity_main.MainActivity;

/**
 * Shows the add book screen
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final EditText titleText = (EditText) findViewById(R.id.add_book_title);
        final EditText authorText = (EditText) findViewById(R.id.add_book_author);

        // Fill the fields
        if (MainActivity.settings.rememberLastSearch) {
            titleText.setText(MainActivity.lastSearchTitle);
            authorText.setText(MainActivity.lastSearchAuthor);
        }

        // Set the action bar details
        setTitle(R.string.title_add);

        // Add a book
        Button addButton = (Button) findViewById(R.id.add_book_submit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleText.getText().toString().trim();
                String author = authorText.getText().toString().trim();

                if (title.equals("") && author.equals("")) {
                    new AlertDialog.Builder(AddActivity.this)
                            .setTitle(R.string.add_book_no_entry_title).setMessage(R.string.add_book_no_entry)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }

                MainActivity.lastSearchTitle = title;
                MainActivity.lastSearchAuthor = author;

                Intent addIntent = new Intent(AddActivity.this, SearchActivity.class);
                addIntent.putExtra("title", title);
                addIntent.putExtra("author", author);
                startActivity(addIntent);
            }
        });

        // Add a book manually instead
        Button manualButton = (Button) findViewById(R.id.add_book_manual_button);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manualIntent = new Intent(AddActivity.this, AddManualActivity.class);
                manualIntent.putExtra("title", titleText.getText().toString());
                manualIntent.putExtra("author", authorText.getText().toString());
                startActivity(manualIntent);
            }
        });
    }
}
