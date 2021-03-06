package com.chesak.adam.readinglist.shared;

import android.content.Context;

import com.chesak.adam.readinglist.activity_main.MainActivity;
import com.chesak.adam.readinglist.data.BookList;
import com.chesak.adam.readinglist.data.SettingsData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Saves and reads application data.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
final public class IO {


    final private static String DATA_FILE_NAME = "book_data";
    final private static String SETTINGS_FILE_NAME = "settings_data";


    /**
     * Reads the data from storage
     * @param context context
     * @return data
     */
    public BookList readData(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        BookList defaultList = new BookList();
        try {
            fis = context.openFileInput(DATA_FILE_NAME);
            is = new ObjectInputStream(fis);
            BookList data = (BookList) is.readObject();
            is.close();
            fis.close();
            return data;
        } catch (FileNotFoundException e) {
            return defaultList;
        } catch (IOException e) {
            return defaultList;
        } catch (ClassNotFoundException e) {
            return defaultList;
        } finally {
            if (fis != null && is != null) {
                try {
                    fis.close();
                    is.close();
                } catch (Exception e) {
                    // Nothing done here
                }
            }
        }
    }


    /**
     * Writes the data to storage
     * @param context context
     */
    public void saveData(Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(MainActivity.bookList);
        } catch (IOException e) {
            // Nothing to do here
        } finally {
            try {
                os.close();
                fos.close();
            } catch (IOException e) {
                //Nothing to do here
            }
        }
    }


    /**
     * Reads the settings from storage
     * @param context context
     * @return settings
     */
    public SettingsData readSettings(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        SettingsData defaultSettings = new SettingsData();
        try {
            fis = context.openFileInput(SETTINGS_FILE_NAME);
            is = new ObjectInputStream(fis);
            SettingsData data = (SettingsData) is.readObject();
            is.close();
            fis.close();
            return data;
        } catch (FileNotFoundException e) {
            return defaultSettings;
        } catch (IOException e) {
            return defaultSettings;
        } catch (ClassNotFoundException e) {
            return defaultSettings;
        } finally {
            if (fis != null && is != null) {
                try {
                    fis.close();
                    is.close();
                } catch (Exception e) {
                    // Nothing done here
                }
            }
        }
    }


    /**
     * Writes the settings to storage
     * @param context context
     */
    public void saveSettings(Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(MainActivity.settings);
        } catch (IOException e) {
            // Nothing to do here
        } finally {
            try {
                os.close();
                fos.close();
            } catch (IOException e) {
                //Nothing to do here
            }
        }
    }

}
