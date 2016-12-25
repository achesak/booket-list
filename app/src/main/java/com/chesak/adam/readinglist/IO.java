package com.chesak.adam.readinglist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Saves and reads application data.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class IO {

    // File names:
    final private static String DATA_FILE_NAME = "book_data";


    /**
     * Reads the data from storage
     * @param context calling activity class
     * @return data
     */
    public static ArrayList<Book> readData(Context context) {
        try {
            FileInputStream fis = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<Book> data = (ArrayList<Book>) is.readObject();
            is.close();
            fis.close();
            return data;
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }


    /**
     * Writes the data to storage
     * @param context calling activity class
     * @param data data
     */
    public static void saveData(Context context, BookList data) {
        try {
            FileOutputStream fos = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
        } catch (IOException e) {
            // Nothing to do here for now.
        }
    }

}
