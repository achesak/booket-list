package com.chesak.adam.readinglist;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Shows the lowest rated books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class RatingLowestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the action bar details
        setTitle("Lowest rated");

        final BookList lowestRated = MainActivity.bookList.getLowestRated();

        // Display the book list
        ListView listView = (ListView) findViewById(R.id.list_rating);
        BookListRatingAdapter adapter = new BookListRatingAdapter(this, lowestRated);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookData selectedBookData = MainActivity.bookList.getIndex(lowestRated.get(position));
                if (selectedBookData == null) {
                    new AlertDialog.Builder(RatingLowestActivity.this)
                            .setTitle(R.string.rating_not_found_title).setMessage(R.string.rating_not_found)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }

                Intent detailIntent = new Intent(RatingLowestActivity.this, BookDetailActivity.class);
                detailIntent.putExtra("position", selectedBookData.getIndex());
                detailIntent.putExtra("book", selectedBookData.getBook());
                detailIntent.putExtra("source", selectedBookData.getSource());
                startActivity(detailIntent);
            }
        });
    }
}