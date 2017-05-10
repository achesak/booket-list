package com.chesak.adam.readinglist;

import java.io.Serializable;

/**
 * Stores the internal settings in a serializable object for easy storage and use.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
final public class SettingsData implements Serializable {


    // Settings fields:
    public int pageRate;


    /**
     * Constructor: default
     */
    public SettingsData() {
        this.pageRate = 0;
    }

}
