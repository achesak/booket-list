package com.chesak.adam.readinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Shows the manual entry add book screen
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookAddManualActivity extends AppCompatActivity {

    EditText titleText;
    EditText authorText;
    EditText pageCountText;
    EditText publisherText;
    EditText publishedText;
    EditText isbnText;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add_manual);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the action bar details
        setTitle(R.string.title_add);

        titleText = (EditText) findViewById(R.id.add_book_manual_title);
        authorText = (EditText) findViewById(R.id.add_book_manual_author);
        pageCountText = (EditText) findViewById(R.id.add_book_manual_page_count);
        publisherText = (EditText) findViewById(R.id.add_book_manual_publisher);
        publishedText = (EditText) findViewById(R.id.add_book_manual_published);
        isbnText = (EditText) findViewById(R.id.add_book_manual_isbn);

        // Fill fields from last screen.
        titleText.setText(getIntent().getStringExtra("title"));
        authorText.setText(getIntent().getStringExtra("author"));

        // Add data when button is clicked
        Button submitButton = (Button) findViewById(R.id.add_book_manual_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the fields
                String title = titleText.getText().toString().trim();
                String author = authorText.getText().toString().trim();
                String pageCountInput = pageCountText.getText().toString();
                int pageCount = Integer.parseInt(pageCountInput);
                if (pageCount == 0) {
                    pageCount = 1;
                }
                String publisher = publisherText.getText().toString().trim();
                String published = publishedText.getText().toString();
                String isbn = isbnText.getText().toString().trim();

                if (title.equals("") || author.equals("") || pageCountInput.equals("")) {
                    new AlertDialog.Builder(BookAddManualActivity.this)
                            .setTitle(R.string.add_book_manual_no_entry_title).setMessage(R.string.add_book_manual_no_entry)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }

                // Add the book
                Book book = new Book(title, author, pageCount, isbn, publisher, published);
                MainActivity.bookList.add(book);
                MainActivity.io.saveData(BookAddManualActivity.this);

                Intent viewMainIntent = new Intent(context, MainActivity.class);
                startActivity(viewMainIntent);
            }
        });
    }
}
