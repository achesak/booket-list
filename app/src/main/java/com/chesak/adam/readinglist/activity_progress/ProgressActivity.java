package com.chesak.adam.readinglist.activity_progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_detail.DetailActivity;
import com.chesak.adam.readinglist.activity_main.BookListAdapter;
import com.chesak.adam.readinglist.activity_main.MainActivity;
import com.chesak.adam.readinglist.data.BookData;
import com.chesak.adam.readinglist.data.BookList;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ListView listView = (ListView) findViewById(R.id.list_progress);

        // Set the title
        setTitle(R.string.title_progress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                Intent detailIntent = new Intent(ProgressActivity.this, DetailActivity.class);
                detailIntent.putExtra("position", selectedBookData.getIndex());
                detailIntent.putExtra("book", selectedBookData.getBook());
                detailIntent.putExtra("source", selectedBookData.getSource());
                startActivity(detailIntent);
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
