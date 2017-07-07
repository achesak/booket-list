package com.chesak.adam.readinglist.activity_finished;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chesak.adam.readinglist.activity_main.MainActivity;
import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_detail.DetailActivity;
import com.chesak.adam.readinglist.data.Book;
import com.chesak.adam.readinglist.data.Constants;

public class FinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ListView listView = (ListView) findViewById(R.id.list_finished);

        // Set the action bar details
        setTitle("Finished books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the book list
        BookListFinishedAdapter adapter = new BookListFinishedAdapter(this, MainActivity.bookList);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book selectedBook = MainActivity.bookList.getFinished(position);
                Intent detailIntent = new Intent(FinishedActivity.this, DetailActivity.class);
                detailIntent.putExtra("position", position);
                detailIntent.putExtra("book", selectedBook);
                detailIntent.putExtra("source", Constants.SOURCE_FINISHED);
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
