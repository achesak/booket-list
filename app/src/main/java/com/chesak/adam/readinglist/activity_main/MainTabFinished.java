package com.chesak.adam.readinglist.activity_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_detail.DetailActivity;
import com.chesak.adam.readinglist.data.Book;
import com.chesak.adam.readinglist.data.Constants;


/**
 * Main view: current books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class MainTabFinished extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_main_finished, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_finished);
        final Context context = view.getContext();

        // Display the book list
        MainActivity.finishedAdapter = new BookListFinishedAdapter(context, MainActivity.bookList);
        listView.setAdapter(MainActivity.finishedAdapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book selectedBook = MainActivity.bookList.getFinished(position);
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("position", position);
                detailIntent.putExtra("book", selectedBook);
                detailIntent.putExtra("source", Constants.SOURCE_FINISHED);
                startActivity(detailIntent);
            }
        });

        return view;
    }
}