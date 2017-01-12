package com.chesak.adam.readinglist;

/**
 * Used to store book data for easy use between screens and contexts.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookData {

    private Book book;
    private int index;
    private int source;


    /**
     * Constructor
     * @param book book
     * @param index index
     */
    public BookData(Book book, int index) {
        this.book = book;
        this.index = index;
    }


    /**
     * Constructor
     * @param book book
     * @param index index
     * @param source source
     */
    public BookData(Book book, int index, int source) {
        this(book, index);
        this.source = source;
    }


    /**
     * Gets the index
     * @return index
     */
    public int getIndex() {
        return index;
    }


    /**
     * Gets the book
     * @return book
     */
    public Book getBook() {
        return book;
    }


    /**
     * Gets the source
     * @return source
     */
    public int getSource() {
        return source;
    }
}
