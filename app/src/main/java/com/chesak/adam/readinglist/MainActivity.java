package com.chesak.adam.readinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;

/**
 * Main activity, shows the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class MainActivity extends AppCompatActivity {







    //https://openlibrary.org/dev/docs/api/search
    //https://openlibrary.org/dev/docs/api/books






    // Application data:
    public static IO io;
    public static BookList bookList = new BookList();

    // UI elements:
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the interface
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list_book);

        if (io == null) {
            io = new IO(getFilesDir());
            bookList = io.readData(MainActivity.this);
        }

        io.saveData(MainActivity.this, bookList);

        // Set the action bar details
        setTitle("Current books");

        // Display the book list
        BookListAdapter adapter = new BookListAdapter(this, bookList);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book selectedBook = bookList.get(position);
                Intent detailIntent = new Intent(MainActivity.this, BookDetailActivity.class);
                detailIntent.putExtra("position", position);
                detailIntent.putExtra("book", selectedBook);
                startActivity(detailIntent);
            }
        });

        // Show add screen
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, BookAddActivity.class);
                startActivity(addIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
