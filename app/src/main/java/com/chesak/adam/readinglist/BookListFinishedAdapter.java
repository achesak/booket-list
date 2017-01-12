package com.chesak.adam.readinglist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for displaying the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookListFinishedAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private BookList dataSource;


    /**
     * Constructor
     * @param context usually "this"
     * @param books list of books
     */
    public BookListFinishedAdapter(Context context, BookList books) {
        this.context = context;
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.list_book_item, parent, false);

        TextView titleElement = (TextView) rowView.findViewById(R.id.list_book_title);
        TextView authorElement = (TextView) rowView.findViewById(R.id.list_book_author);
        TextView pagesElement = (TextView) rowView.findViewById(R.id.list_book_pages);
        ImageView coverElement = (ImageView) rowView.findViewById(R.id.list_book_thumbnail);

        Book book = (Book) getItem(position);

        String author = book.getAuthor();
        if (author.indexOf('\n') != -1) {
            author = book.getAuthor().split("\n")[0] + " et al.";
        }
        String pageCounter = String.format("%d / %d (%d%%)", book.getPageRead(), book.getPageCount(), book.getProgress());

        titleElement.setText(book.getTitle());
        authorElement.setText(author);
        pagesElement.setText(pageCounter);

        // Set the image
        if (!book.getThumbnailUrl().equals("")) {
            Bitmap image = OpenLibraryClient.getImage(book.getThumbnailUrl());
            if (image != null) {
                coverElement.setImageBitmap(image);
            } else {
                new DownloadImageTask(coverElement, book.getThumbnailUrl()).execute(book.getThumbnailUrl());
            }
            coverElement.setBackgroundResource(0);
        }

        return rowView;
    }
}
