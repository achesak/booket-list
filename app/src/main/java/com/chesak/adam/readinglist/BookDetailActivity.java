package com.chesak.adam.readinglist;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        TextView detailProgress = (TextView) findViewById(R.id.detail_progess);
        TextView detailSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        RatingBar detailRating = (RatingBar) findViewById(R.id.detail_rating);
        TextView detailIsbn = (TextView) findViewById(R.id.detail_isbn);

        // Get the selected book.
        int position = getIntent().getIntExtra("position", 0);
        Book book = (Book) getIntent().getSerializableExtra("book");

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
        String progress = String.format("%d / %d (started reading %s)", book.getPageRead(), book.getPageCount(),book.getStartedReadingFormatted());
        detailProgress.setText(progress);
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
    }
}
