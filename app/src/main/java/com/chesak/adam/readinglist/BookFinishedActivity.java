package com.chesak.adam.readinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class BookFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_finished);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ListView listView = (ListView) findViewById(R.id.list_finished);

        // Set the action bar details
        setTitle("Finished books");

        // Display the book list
        BookListFinishedAdapter adapter = new BookListFinishedAdapter(this, MainActivity.bookList);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book selectedBook = MainActivity.bookList.getFinished(position);
                Intent detailIntent = new Intent(BookFinishedActivity.this, BookDetailActivity.class);
                detailIntent.putExtra("position", position);
                detailIntent.putExtra("book", selectedBook);
                detailIntent.putExtra("source", ReadingListConstants.SOURCE_FINISHED);
                startActivity(detailIntent);
            }
        });
    }
}
