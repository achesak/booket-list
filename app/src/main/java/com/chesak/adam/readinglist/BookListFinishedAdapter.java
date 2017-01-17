package com.chesak.adam.readinglist;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Adapter for displaying the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookListFinishedAdapter extends BookListAdapter {

    /**
     * Constructor
     * @param context usually "this"
     * @param books list of books
     */
    public BookListFinishedAdapter(Context context, BookList books) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataSource = books;
    }

    @Override
    public int getCount() {
        return dataSource.sizeFinished();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.getFinished(position);
    }
}
