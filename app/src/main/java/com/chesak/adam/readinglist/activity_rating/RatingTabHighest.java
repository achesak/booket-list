package com.chesak.adam.readinglist.activity_rating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chesak.adam.readinglist.R;
import com.chesak.adam.readinglist.activity_detail.DetailActivity;
import com.chesak.adam.readinglist.activity_main.MainActivity;
import com.chesak.adam.readinglist.data.BookData;
import com.chesak.adam.readinglist.data.BookList;


/**
 * Rating view: highest rated
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class RatingTabHighest extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_rating_high, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listview_rating_high);

        final BookList highestRated = MainActivity.bookList.getHighestRated();
        final Context context = view.getContext();

        // Display the book list
        BookListRatingAdapter adapter = new BookListRatingAdapter(context, highestRated);
        listView.setAdapter(adapter);

        // Show book details on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookData selectedBookData = MainActivity.bookList.getIndex(highestRated.get(position));
                if (selectedBookData == null) {
                    new AlertDialog.Builder(context)
                            .setTitle(R.string.rating_not_found_title).setMessage(R.string.rating_not_found)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("position", selectedBookData.getIndex());
                detailIntent.putExtra("book", selectedBookData.getBook());
                detailIntent.putExtra("source", selectedBookData.getSource());
                startActivity(detailIntent);
            }
        });

        return view;
    }
}