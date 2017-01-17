package com.chesak.adam.readinglist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Adapter for displaying the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookListAdapter extends BaseAdapter {


    protected LayoutInflater inflater;
    protected BookList dataSource;


    static class ViewHolder {
        private TextView titleElement;
        private TextView authorElement;
        private TextView pagesElement;
        private ImageView coverElement;
    }


    /**
     * Constructor
     * @param context usually "this"
     * @param books list of books
     */
    public BookListAdapter(Context context, BookList books) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataSource = books;
    }


    /**
     * Default constructor (unused)
     */
    public BookListAdapter() {

    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_book_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleElement = (TextView) convertView.findViewById(R.id.list_book_title);
            viewHolder.authorElement = (TextView) convertView.findViewById(R.id.list_book_author);
            viewHolder.pagesElement = (TextView) convertView.findViewById(R.id.list_book_pages);
            viewHolder.coverElement = (ImageView) convertView.findViewById(R.id.list_book_thumbnail);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book book = (Book) getItem(position);

        String author = book.getAuthor();
        if (author.indexOf('\n') != -1) {
            author = book.getAuthor().split("\n")[0] + " et al.";
        }
        String pageCounter = String.format(Locale.US, "%d / %d (%d%%)", book.getPageRead(), book.getPageCount(), book.getProgress());

        viewHolder.titleElement.setText(book.getTitle());
        viewHolder.authorElement.setText(author);
        viewHolder.pagesElement.setText(pageCounter);

        // Set the image
        if (!book.getThumbnailUrl().equals("")) {
            Bitmap image = OpenLibraryClient.getImage(book.getThumbnailUrl());
            if (image != null) {
                viewHolder.coverElement.setImageBitmap(image);
            } else {
                new DownloadImageTask(viewHolder.coverElement, book.getThumbnailUrl()).execute(book.getThumbnailUrl());
            }
            viewHolder.coverElement.setBackgroundResource(0);
        }

        return convertView;
    }
}
