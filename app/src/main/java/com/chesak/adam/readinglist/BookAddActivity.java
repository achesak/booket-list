package com.chesak.adam.readinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookAddActivity extends AppCompatActivity {

    final private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Set the action bar details
        setTitle("Add book");

        Button manualButton = (Button) findViewById(R.id.add_book_manual_button);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manualIntent = new Intent(context, BookAddManualActivity.class);
                startActivity(manualIntent);
            }
        });
    }
}
