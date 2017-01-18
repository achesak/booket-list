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
final public class OpenLibraryClient {

    // URL constants:
    final private static String SEARCH_URL = "http://openlibrary.org/search.json?";
    final private static String BOOK_URL = "http://openlibrary.org/api/books?jscmd=data&format=json&";
    final private static String THUMBNAIL_URL = "http://covers.openlibrary.org/b/id/";

    // HTTP client:
    private static AsyncHttpClient client = new AsyncHttpClient();


    /**
     * Searches for books
     * @param title title
     * @param author author
     * @param params parameters
     * @param responseHandler response handler
     */
    public static void getSearch(String title, String author, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getSearchUrl(title, author), params, responseHandler);
    }


    /**
     * Gets a book's details
     * @param key key
     * @param params parameters
     * @param responseHandler response handler
     */
    public static void getBook(String key, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getBookUrl(key), params, responseHandler);
    }


    /**
     * Gets a book's cover thumbnail URL
     * @param key key
     * @return thumbnail URL
     */
    public static String getCoverThumbnail(String key) {
        return THUMBNAIL_URL + key + "-" + "M.jpg";
    }


    /**
     * Gets a book's cover URL
     * @param key key
     * @return cover URL
     */
    public static String getCover(String key) {
        return THUMBNAIL_URL + key + "-" + "L.jpg";
    }


    /**
     * Gets a search URL
     * @param title title
     * @param author author
     * @return URL
     */
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


    /**
     * Gets a details URL
     * @param key key
     * @return URL
     */
    private static String getBookUrl(String key) {
        return BOOK_URL + "bibkeys=OLID:" + key;
    }


    /**
     * Downloads an image
     * @param url URL
     * @return image
     */
    public static Bitmap getImage(String url) {
        synchronized (MainActivity.imageCache) {
            Object o = MainActivity.imageCache.get(url);
            if (o != null) {
                return (Bitmap) o;
            } else {
                return null;
            }
        }
    }
}