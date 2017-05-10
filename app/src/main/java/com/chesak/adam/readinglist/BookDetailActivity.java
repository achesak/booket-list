package com.chesak.adam.readinglist;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Locale;

/**
 * Shows the book details
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
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
        TextView detailDays = (TextView) findViewById(R.id.detail_days);
        TextView detailStarted = (TextView) findViewById(R.id.detail_started);
        TextView detailSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        RatingBar detailRating = (RatingBar) findViewById(R.id.detail_rating);
        TextView detailIsbn = (TextView) findViewById(R.id.detail_isbn);

        Button updateButton = (Button) findViewById(R.id.detail_update);
        Button removeButton = (Button) findViewById(R.id.detail_remove);

        // Get the selected book.
        final int position = getIntent().getIntExtra("position", 0);
        final int source = getIntent().getIntExtra("source", 0);
        final Book book = (Book) getIntent().getSerializableExtra("book");

        // Set the action bar details
        setTitle(book.getTitle());

        // Set the fields
        if (!book.getImageUrl().equals("")) {
            Bitmap image = OpenLibraryClient.getImage(book.getImageUrl());
            if (image != null) {
                detailCover.setImageBitmap(image);
            } else {
                new DownloadImageTask(detailCover, book.getImageUrl()).execute(book.getImageUrl());
            }
            detailCover.setBackgroundResource(0);
        }
        detailTitle.setText(book.getTitle());
        detailAuthor.setText(book.getAuthor());
        String publisher = "No publisher provided";
        if (!book.getPublisher().equals("")) {
            publisher = book.getPublisher();
        }
        if (!book.getPublishDate().equals("")) {
            publisher += ", " + book.getPublishDate();
        }
        detailPublisher.setText(publisher);
        String progress = String.format(Locale.US, "%d / %d (%d%%)", book.getPageRead(), book.getPageCount(),book.getProgress());
        detailProgress.setText(progress);
        if (source == ReadingListConstants.SOURCE_FINISHED || MainActivity.settings.pageRate < 1) {
            detailDays.setVisibility(View.GONE);
        } else {
            int daysRemaining = (book.getPageCount() - book.getPageRead()) / MainActivity.settings.pageRate;
            detailDays.setText(getString(R.string.detail_days, daysRemaining, MainActivity.settings.pageRate));
        }
        detailStarted.setText(getString(R.string.detail_started, book.getStartedReadingFormatted()));
        if (book.getSynopsis().equals("")) {
            detailSynopsis.setText(R.string.detail_no_synopsis);
        } else {
            detailSynopsis.setText(book.getSynopsis());
        }
        detailRating.setRating(book.getUserRating());
        if (book.getIsbn().equals("")) {
            detailIsbn.setText(R.string.detail_no_isbn);
        } else {
            detailIsbn.setText(getString(R.string.detail_isbn,book.getIsbn()));
        }

        // Save book details on rating change
        detailRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (source == ReadingListConstants.SOURCE_MAIN) {
                    MainActivity.bookList.get(position).setUserRating(rating);
                } else if (source == ReadingListConstants.SOURCE_FINISHED) {
                    MainActivity.bookList.getFinished(position).setUserRating(rating);
                }
                MainActivity.io.saveData(BookDetailActivity.this);
            }
        });

        // Update page count
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View npView = View.inflate(BookDetailActivity.this, R.layout.number_picker_dialog, null);
                final TextView pagesReadText = (TextView) npView.findViewById(R.id.number_picker);
                pagesReadText.setText(String.format(Locale.US, "%d", book.getPageRead()));
                new AlertDialog.Builder(BookDetailActivity.this)
                        .setTitle("Pages read")
                        .setView(npView)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int originalPagesRead = book.getPageRead();

                                        // Get the new pages read input
                                        String pagesReadInput = pagesReadText.getText().toString().trim();
                                        if (pagesReadInput.equals("")) {
                                            return;
                                        }
                                        int pagesRead = Integer.parseInt(pagesReadInput);
                                        if (pagesRead > book.getPageCount()) {
                                            pagesRead = book.getPageCount();
                                        }
                                        if (pagesRead < 0) {
                                            pagesRead = 0;
                                        }

                                        // Set the pages read
                                        book.setPageRead(pagesRead);
                                        if (originalPagesRead != book.getPageCount()) {
                                            MainActivity.bookList.get(position).setPageRead(pagesRead);
                                        } else {
                                            MainActivity.bookList.getFinished(position).setPageRead(pagesRead);
                                        }

                                        // Move to other list as needed
                                        if (pagesRead == book.getPageCount()) {
                                            MainActivity.bookList.moveToFinished(position);
                                        } else if (originalPagesRead == book.getPageCount() && pagesRead < originalPagesRead) {
                                            MainActivity.bookList.moveToReading(position);
                                        }

                                        // Save and switch to main view
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
                        .setTitle(R.string.confirm_remove_book_title).setMessage(R.string.confirm_remove_book)
                        .setPositiveButton(R.string.dialog_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (!book.isFinishedReading()) {
                                            MainActivity.bookList.remove(position);
                                        } else {
                                            MainActivity.bookList.removeFinished(position);
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
    }
}
