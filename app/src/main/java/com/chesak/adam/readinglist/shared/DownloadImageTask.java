package com.chesak.adam.readinglist.shared;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.chesak.adam.readinglist.activity_main.MainActivity;

import java.io.InputStream;

/**
 * Async downloads an image
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
final public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView bmImage;
    private String imageURL;

    public DownloadImageTask(ImageView bmImage, String imageURL) {
        this.bmImage = bmImage;
        this.imageURL = imageURL;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        MainActivity.imageCache.put(imageURL, result);
    }
}