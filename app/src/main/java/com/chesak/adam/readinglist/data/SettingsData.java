package com.chesak.adam.readinglist.data;

import java.io.Serializable;

/**
 * Stores the internal settings in a serializable object for easy storage and use.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
final public class SettingsData implements Serializable {


    // Settings fields:
    public int pageRate = 0;
    public boolean rememberLastSearch = true;


    /**
     * Constructor: default
     */
    public SettingsData() {
        this.pageRate = 0;
        this.rememberLastSearch = true;
    }

}
