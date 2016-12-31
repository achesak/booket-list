package com.chesak.adam.readinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.IOException;

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
        setTitle("Add book");

        titleText = (EditText) findViewById(R.id.add_book_manual_title);
        authorText = (EditText) findViewById(R.id.add_book_manual_author);
        pageCountText = (EditText) findViewById(R.id.add_book_manual_page_count);
        publisherText = (EditText) findViewById(R.id.add_book_manual_publisher);
        publishedText = (EditText) findViewById(R.id.add_book_manual_published);
        isbnText = (EditText) findViewById(R.id.add_book_manual_isbn);

        // Add data when button is clicked
        Button submitButton = (Button) findViewById(R.id.add_book_manual_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the fields
                String title = titleText.getText().toString().trim();
                String author = authorText.getText().toString().trim();
                String pageCountInput = pageCountText.getText().toString();
                int pageCount = pageCountInput.equals("") ? 0 : Integer.parseInt(pageCountInput);
                String publisher = publisherText.getText().toString().trim();
                String published = publishedText.getText().toString();
                String isbn = isbnText.getText().toString().trim();

                if (title.equals("") || author.equals("") || pageCountInput.equals("")) {
                    new AlertDialog.Builder(BookAddManualActivity.this)
                            .setTitle("Add book").setMessage("Title, author, and page count cannot be left blank.")
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
