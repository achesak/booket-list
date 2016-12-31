package com.chesak.adam.readinglist;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ImageView detailCover = (ImageView) findViewById(R.id.detail_cover);
        TextView detailTitle = (TextView) findViewById(R.id.detail_title);
        TextView detailAuthor = (TextView) findViewById(R.id.detail_author);
        TextView detailPublisher = (TextView) findViewById(R.id.detail_publisher);
        final TextView detailProgress = (TextView) findViewById(R.id.detail_progess);
        TextView detailStarted = (TextView) findViewById(R.id.detail_started);
        TextView detailSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        RatingBar detailRating = (RatingBar) findViewById(R.id.detail_rating);
        TextView detailIsbn = (TextView) findViewById(R.id.detail_isbn);

        Button updateButton = (Button) findViewById(R.id.detail_update);
        Button removeButton = (Button) findViewById(R.id.detail_remove);

        // Get the selected book.
        final int position = getIntent().getIntExtra("position", 0);
        final Book book = (Book) getIntent().getSerializableExtra("book");

        // Set the action bar details
        setTitle(book.getTitle());

        // Set the fields
        if (!book.getImageUrl().equals("")) {
            new DownloadImageTask(detailCover).execute(book.getImageUrl());
        }
        detailTitle.setText(book.getTitle());
        detailAuthor.setText(book.getAuthor());
        String publisher = "No publisher provided";
        if (!book.getPublisher().equals("")) {
            if (book.getPublishDate().equals("")) {
                publisher = book.getPublisher();
            } else {
                publisher = book.getPublisher() + ", " + book.getPublishDate();
            }
        }
        detailPublisher.setText(publisher);
        String progress = String.format("%d / %d (%d%%)", book.getPageRead(), book.getPageCount(),book.getProgress());
        detailProgress.setText(progress);
        detailStarted.setText("Added " + book.getStartedReadingFormatted());
        if (book.getSynopsis().equals("")) {
            detailSynopsis.setText("No description provided");
        } else {
            detailSynopsis.setText(book.getSynopsis());
        }
        detailRating.setRating(book.getUserRating());
        if (book.getIsbn().equals("")) {
            detailIsbn.setText("No ISBN provided");
        } else {
            detailIsbn.setText("ISBN: " + book.getIsbn());
        }

        // Save book details on rating change
        detailRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                MainActivity.bookList.get(position).setUserRating(rating);
                MainActivity.io.saveData(BookDetailActivity.this);
            }
        });

        // Update page count
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View npView = inflater.inflate(R.layout.number_picker_dialog, null);
                final TextView pagesReadText = (TextView) npView.findViewById(R.id.number_picker);
                pagesReadText.setText("" + book.getPageRead());
                new AlertDialog.Builder(BookDetailActivity.this)
                        .setTitle("Pages read")
                        .setView(npView)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String pagesReadInput = pagesReadText.getText().toString().trim();
                                        if (pagesReadInput.equals("")) {
                                            return;
                                        }
                                        int pagesRead = Integer.parseInt(pagesReadInput);
                                        if (pagesRead > book.getPageCount()) {
                                            pagesRead = book.getPageCount();
                                        }
                                        book.setPageRead(pagesRead);
                                        MainActivity.bookList.get(position).setPageRead(pagesRead);
                                        if (pagesRead == book.getPageCount()) {
                                            MainActivity.bookList.move(position);
                                        }
                                        MainActivity.io.saveData(BookDetailActivity.this);
                                        Intent mainIntent = new Intent(BookDetailActivity.this, MainActivity.class);
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
            }
        });

        // Remove book from list
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(BookDetailActivity.this)
                        .setTitle("Remove book").setMessage(R.string.confirm_remove_book)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        MainActivity.bookList.remove(position);
                                        MainActivity.io.saveData(BookDetailActivity.this);
                                        Intent mainIntent = new Intent(BookDetailActivity.this, MainActivity.class);
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
            }
        });
    }
}
