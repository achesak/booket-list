package com.chesak.adam.readinglist;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Makes requests to the open library API:
 * https://openlibrary.org/dev/docs/api/search
 * https://openlibrary.org/dev/docs/api/books
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class OpenLibraryClient {

    final private static String SEARCH_URL = "http://openlibrary.org/search.json?";
    final private static String BOOK_URL = "http://openlibrary.org/api/books?jscmd=data&format=json&";
    final private static String THUMBNAIL_URL = "http://covers.openlibrary.org/b/id/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getSearch(String title, String author, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getSearchUrl(title, author), params, responseHandler);
    }

    public static void getBook(String key, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getBookUrl(key), params, responseHandler);
    }

    public static String getCoverThumbnail(String key) {
        return THUMBNAIL_URL + key + "-" + "M.jpg";
    }

    public static String getCover(String key) {
        return THUMBNAIL_URL + key + "-" + "L.jpg";
    }

    private static String getSearchUrl(String title, String author) {
        String url = SEARCH_URL;
        try {
            if (!title.equals("")) {
                url += "title=" + URLEncoder.encode(title, "UTF-8") + "&";
            }
            if (!author.equals("")) {
                url += "author=" + URLEncoder.encode(author, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            // Nothing to do here
        }
        return url;
    }

    private static String getBookUrl(String key) {
        return BOOK_URL + "bibkeys=OLID:" + key;
    }

    public static Bitmap getImage(String thumbnailUrl) {
        synchronized (MainActivity.imageCache) {
            Object o = MainActivity.imageCache.get(thumbnailUrl);
            if (o != null) {
                return (Bitmap) o;
            } else {
                return null;
            }
        }
    }
}