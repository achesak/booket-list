package com.chesak.adam.readinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ListView listView = (ListView) findViewById(R.id.list_progress);

        // Set the title
        setTitle(R.string.title_progress);

        final BookList progressList = MainActivity.bookList.getProgressList();

        // Display the book list
        BookListAdapter adapter = new BookListAdapter(this, progressList);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookData selectedBookData = MainActivity.bookList.getIndex(progressList.get(position));
                if (selectedBookData == null) {
                    new AlertDialog.Builder(ProgressActivity.this)
                            .setTitle(R.string.rating_not_found_title).setMessage(R.string.rating_not_found)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }

                Intent detailIntent = new Intent(ProgressActivity.this, BookDetailActivity.class);
                detailIntent.putExtra("position", selectedBookData.getIndex());
                detailIntent.putExtra("book", selectedBookData.getBook());
                detailIntent.putExtra("source", selectedBookData.getSource());
                startActivity(detailIntent);
            }
        });
    }
}
