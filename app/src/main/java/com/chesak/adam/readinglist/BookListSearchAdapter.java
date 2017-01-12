package com.chesak.adam.readinglist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Adapter for displaying the list of books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookListSearchAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private JSONArray dataSource;


    /**
     * Constructor
     * @param context usually "this"
     * @param books list of books
     */
    public BookListSearchAdapter(Context context, JSONArray books) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataSource = books;
    }

    @Override
    public int getCount() {
        return dataSource.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return dataSource.get(position);
        } catch (JSONException e) {
            return null;
        }
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
        ImageView coverElement = (ImageView) rowView.findViewById(R.id.list_book_thumbnail);

        JSONObject book = (JSONObject) getItem(position);

        String author = "";
        try {
            author = book.getJSONArray("author_name").get(0).toString();
            if (book.getJSONArray("author_name").length() > 1) {
                author += " et al.";
            }
        } catch (JSONException e) {
            // Nothing to do here
        }

        String title = "";
        try {
            title = book.get("title").toString();
        } catch (JSONException e) {
            // Nothing to do here
        }

        String key = "";
        try {
            key = book.get("cover_i").toString();
        } catch (JSONException e) {
            // Nothing to do here
        }

        titleElement.setText(title);
        authorElement.setText(author);

        if (!key.equals("")) {
            String thumbnailUrl = OpenLibraryClient.getCoverThumbnail(key);
            Bitmap image = OpenLibraryClient.getImage(thumbnailUrl);
            if (image != null) {
                coverElement.setImageBitmap(image);
            } else {
                new DownloadImageTask(coverElement, thumbnailUrl).execute(thumbnailUrl);
            }
            coverElement.setBackgroundResource(0);
        }

        return rowView;
    }


}
