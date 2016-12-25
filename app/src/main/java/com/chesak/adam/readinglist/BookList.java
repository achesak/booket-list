package com.chesak.adam.readinglist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a list of books. Basic wrapper around ArrayList<Book> with some added methods.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookList {


    // Data list:
    private static ArrayList<Book> list;


    /**
     * Used for sorting by highest rating
     */
    private class HighestRatingComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            if (book1.getUserRating() > book2.getUserRating()) {
                return 1;
            } else if (book1.getUserRating() < book2.getUserRating()) {
                return -1;
            } else {
                return 0;
            }
        }
    }


    /**
     * Used for sorting by lowest rating
     */
    private class LowestRatingComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            if (book1.getUserRating() < book2.getUserRating()) {
                return 1;
            } else if (book1.getUserRating() > book2.getUserRating()) {
                return -1;
            } else {
                return 0;
            }
        }
    }


    /**
     * Used for sorting by date added; the usual sorting method
     */
    private class DateAddedComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            return book1.getStartedReading().compareTo(book2.getStartedReading());
        }
    }


    /**
     * Constructor: default
     */
    public BookList() {
        list = new ArrayList<>();
    }


    /**
     * Constructor
     * @param data data
     */
    public BookList(ArrayList<Book> data) {
        list = data;
    }


    /**
     * Gets the entire list
     * @return data
     */
    public ArrayList<Book> getData() {
        return list;
    }


    /**
     * Gets book at index
     * @param index index
     * @return book
     */
    public Book get(int index) {
        return list.get(index);
    }


    /**
     * Adds a book to the list
     * @param book book
     */
    public void add(Book book) {
        list.add(book);
    }


    /**
     * Gets the size of the list
     * @return size
     */
    public int size() {
        return list.size();
    }


    /**
     * Sorts the list by date added
     */
    public void sort() {
        Collections.sort(list, new DateAddedComparator());
    }


    /**
     * Gets the highest rated books
     * @param count number of books to get
     * @return list of books
     */
    public ArrayList<Book> getHighestRated(int count) {
        ArrayList<Book> copy = (ArrayList<Book>) list.clone();
        Collections.sort(copy, new HighestRatingComparator());
        if (count > copy.size()) {
            count = copy.size();
        }
        return (ArrayList<Book>) copy.subList(0, count);
    }


    /**
     * Gets the lowest rated boks
     * @param count number of books to get
     * @return list of books
     */
    public ArrayList<Book> getLowestRated(int count) {
        ArrayList<Book> copy = (ArrayList<Book>) list.clone();
        Collections.sort(copy, new LowestRatingComparator());
        if (count > copy.size()) {
            count = copy.size();
        }
        return (ArrayList<Book>) copy.subList(0, count);
    }

}
