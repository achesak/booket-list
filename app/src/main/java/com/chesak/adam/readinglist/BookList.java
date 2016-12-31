package com.chesak.adam.readinglist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a list of books. Basic wrapper around ArrayList<Book> with some added methods.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class BookList implements Serializable {


    // Data list:
    private ArrayList<Book> list;
    private ArrayList<Book> finishedList;


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
        this.list = new ArrayList<>();
        this.finishedList = new ArrayList<>();
    }


    /**
     * Constructor
     * @param data data
     */
    public BookList(ArrayList<Book> data) {
        this.list = data;
        this.finishedList = new ArrayList<>();
    }


    /**
     * Gets the entire list
     * @return data
     */
    public ArrayList<Book> getData() {
        return list;
    }


    /**
     * Gets the entire finished list
     * @return data
     */
    public ArrayList<Book> getFinishedData() {
        return finishedList;
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
        sort();
    }


    /**
     * Removes a book from the list
     * @param index index
     * @return removed book
     */
    public Book remove(int index) {
        return list.remove(index);
    }


    /**
     * Gets the size of the list
     * @return size
     */
    public int size() {
        return list.size();
    }


    /**
     * Gets book at index
     * @param index index
     * @return book
     */
    public Book getFinished(int index) {
        return finishedList.get(index);
    }


    /**
     * Adds a book to the list
     * @param book book
     */
    public void addFinished(Book book) {
        finishedList.add(book);
        sortFinished();
    }


    /**
     * Removes a book from the list
     * @param index index
     * @return removed book
     */
    public Book removeFinished(int index) {
        return finishedList.remove(index);
    }


    /**
     * Gets the size of the finished list
     * @return size
     */
    public int sizeFinished() {
        return finishedList.size();
    }


    /**
     * Moves a book to the finished list
     * @param index index in list
     */
    public void move(int index) {
        finishedList.add(list.remove(index));
    }


    /**
     * Sorts the list by date added
     */
    public void sort() {
        Collections.sort(list, new DateAddedComparator());
    }


    /**
     * Sorts the finished list by date added
     */
    public void sortFinished() {
        Collections.sort(finishedList, new DateAddedComparator());
    }


    /**
     * Gets the 10 highest rated books
     * @return list of books
     */
    public ArrayList<Book> getHighestRated() {
        return getHighestRated(10);
    }


    /**
     * Gets the 10 lowest rated books
     * @return list of books
     */
    public ArrayList<Book> getLowestRated() {
        return getLowestRated(10);
    }


    /**
     * Gets the highest rated books
     * @param count number of books to get
     * @return list of books
     */
    public ArrayList<Book> getHighestRated(int count) {
        ArrayList<Book> copy = new ArrayList<>();
        copy.addAll(list);
        copy.addAll(finishedList);
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
        ArrayList<Book> copy = new ArrayList<>();
        copy.addAll(list);
        copy.addAll(finishedList);
        Collections.sort(copy, new LowestRatingComparator());
        if (count > copy.size()) {
            count = copy.size();
        }
        return (ArrayList<Book>) copy.subList(0, count);
    }

}
