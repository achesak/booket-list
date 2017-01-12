package com.chesak.adam.readinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private static JSONArray data;
    private static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the action bar details
        setTitle("Search");

        book = new Book();

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");

        final ListView listView = (ListView) findViewById(R.id.list_search);
        final TextView loadingView = (TextView) findViewById(R.id.search_loading_filler);

        OpenLibraryClient.getSearch(title, author, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                try {

                    if (response.getJSONArray("docs").length() != 0) {
                        loadingView.setVisibility(View.GONE);

                        data = response.getJSONArray("docs");
                        BookListSearchAdapter adapter = new BookListSearchAdapter(SearchActivity.this, response.getJSONArray("docs"));
                        listView.setAdapter(adapter);
                    } else {
                        loadingView.setText(R.string.search_no_results);
                    }
                } catch (JSONException e) {
                    // Nothing to do here
                }

            }
        });

        // Add book on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONObject bookData = null;
                String key = "";
                try {
                    bookData = data.getJSONObject(position);
                    if (bookData.has("cover_edition_key")) {
                        key = bookData.getString("cover_edition_key");
                    } else {
                        key = bookData.getJSONArray("edition_key").get(0).toString();
                    }
                    book = new Book();
                    book.setTitle(bookData.getString("title"));
                    JSONArray authors = bookData.getJSONArray("author_name");
                    if (authors.length() == 0) {
                        book.setAuthor("No author provided");
                    } else if (authors.length() == 1) {
                        book.setAuthor(authors.getString(0));
                    } else {
                        String author = "";
                        for (int i = 0; i < authors.length(); i++) {
                            author += authors.getString(i);
                            if (i != authors.length() - 1) {
                                author += "\n";
                            }
                        }
                        book.setAuthor(author);
                    }
                    book.setKey(key);
                    book.setPublishDate(bookData.get("first_publish_year").toString());
                    book.setImageUrl(OpenLibraryClient.getCover(bookData.get("cover_i").toString()));
                    book.setThumbnailUrl(OpenLibraryClient.getCoverThumbnail(bookData.get("cover_i").toString()));
                    if (bookData.has("subtitle")) {
                        book.setSubtitle(bookData.getString("subtitle"));
                    }
                    String firstSentence = "";
                    if (bookData.has("first_sentence")) {
                        firstSentence = bookData.getJSONArray("first_sentence").get(0).toString();
                    }
                    String categories = "";
                    JSONArray categoryList = bookData.getJSONArray("subject");
                    for (int i = 0; i < categoryList.length(); i++) {
                        String row = categoryList.get(i).toString();
                        if (!row.equals("In library") && !row.equals("Accessible book") && !row.equals("Protected DAISY") && !row.toLowerCase().contains("ebook")) {
                            categories += row;
                            if (i + 1 != categoryList.length()) {
                                categories += ", ";
                            }
                        }
                    }
                    if (!firstSentence.equals("")) {
                        categories = firstSentence + "\n\n" + categories;
                    }
                    book.setSynopsis(categories);
                    book.setIsbn(bookData.getJSONArray("isbn").get(0).toString());
                } catch (JSONException e) {
                    // Nothing to do here
                }

                if (bookData == null) {
                    return;
                    // TODO: do something more here later!
                }

                OpenLibraryClient.getBook(key, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Iterator<String> keys = response.keys();
                            String key = keys.next();
                            JSONObject moreData = response.getJSONObject(key);
                            book.setPageCount(moreData.getInt("number_of_pages"));
                            if (book.getPublisher().equals("")) {
                                book.setPublisher(moreData.getJSONArray("publishers").getJSONObject(0).getString("name"));
                            }
                        } catch (JSONException e) {
                            // Nothing to do here
                        }

                        // If no page count was found, prompt the user to enter it manually
                        if (book.getPageCount() == 0) {

                            LayoutInflater inflater = (LayoutInflater)
                                    getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View npView = inflater.inflate(R.layout.page_count_dialog, null);
                            final TextView pageCountText = (TextView) npView.findViewById(R.id.page_count_picker);
                            new AlertDialog.Builder(SearchActivity.this)
                                    .setTitle(R.string.add_book_no_page_count_title)
                                    .setView(npView)
                                    .setPositiveButton(R.string.dialog_ok,
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                    // Get the page count input
                                                    int pageCountInput = Integer.parseInt(pageCountText.getText().toString());
                                                    if (pageCountInput == 0) {
                                                        pageCountInput = 1;
                                                    }
                                                    book.setPageCount(pageCountInput);

                                                    // Save and switch to main view
                                                    MainActivity.bookList.add(book);
                                                    book = new Book();
                                                    MainActivity.io.saveData(SearchActivity.this);

                                                    Intent mainIntent = new Intent(SearchActivity.this, MainActivity.class);
                                                    startActivity(mainIntent);
                                                }
                                            })
                                    .setNegativeButton(R.string.dialog_cancel,
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    // Nothing to do here
                                                }
                                            })
                                    .create().show();

                        } else {

                            MainActivity.bookList.add(book);
                            book = new Book();
                            MainActivity.io.saveData(SearchActivity.this);

                            Intent mainIntent = new Intent(SearchActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }
                    }
                });
            }
        });
    }
}
