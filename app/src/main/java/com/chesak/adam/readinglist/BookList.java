package com.chesak.adam.readinglist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Represents a list of books. Basic wrapper around ArrayList<Book> with some added methods.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
final public class BookList implements Serializable {


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
                return -1;
            } else if (book1.getUserRating() < book2.getUserRating()) {
                return 1;
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
                return -1;
            } else if (book1.getUserRating() > book2.getUserRating()) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    /**
     * Used for sorting by progress
     */
    private class ProgressComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            int p1 = book1.getProgress();
            int p2 = book2.getProgress();
            if (p1 < p2) {
                return 1;
            } else if (p1 > p2) {
                return -1;
            } else {
                return book1.getStartedReading().compareTo(book2.getStartedReading());
            }
        }
    }


    /**
     * Used for sorting the main lists; sort by title, then date added
     */
    private class PrimaryComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            int comparison = book1.getTitle().toLowerCase().compareTo(book2.getTitle().toLowerCase());
            if (comparison != 0) {
                return comparison;
            } else {
                return book1.getStartedReading().compareTo(book2.getStartedReading());
            }
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
    public void moveToFinished(int index) {
        finishedList.add(list.remove(index));
        sortFinished();
    }


    /**
     * Moves a book to the reading list
     * @param index index in list
     */
    public void moveToReading(int index) {
        list.add(finishedList.remove(index));
        sort();
    }


    /**
     * Sorts the list by date added
     */
    public void sort() {
        Collections.sort(list, new PrimaryComparator());
    }


    /**
     * Sorts the finished list by date added
     */
    public void sortFinished() {
        Collections.sort(finishedList, new PrimaryComparator());
    }


    /**
     * Gets the 10 highest rated books
     * @return list of books
     */
    public BookList getHighestRated() {
        return getHighestRated(10);
    }


    /**
     * Gets the 10 lowest rated books
     * @return list of books
     */
    public BookList getLowestRated() {
        return getLowestRated(10);
    }


    /**
     * Gets the highest rated books
     * @param count number of books to get
     * @return list of books
     */
    public BookList getHighestRated(int count) {
        ArrayList<Book> copy = new ArrayList<>();
        copy.addAll(list);
        copy.addAll(finishedList);
        Collections.sort(copy, new HighestRatingComparator());
        if (count > copy.size()) {
            count = copy.size();
        }
        return new BookList(new ArrayList<>(copy.subList(0, count)));
    }


    /**
     * Gets the lowest rated books
     * @param count number of books to get
     * @return list of books
     */
    public BookList getLowestRated(int count) {
        ArrayList<Book> copy = new ArrayList<>();
        copy.addAll(list);
        copy.addAll(finishedList);
        Collections.sort(copy, new LowestRatingComparator());
        if (count > copy.size()) {
            count = copy.size();
        }
        return new BookList(new ArrayList<>(copy.subList(0, count)));
    }


    /**
     * Gets the list of books sorted by progress
     * @return list of books
     */
    public BookList getProgressList() {
        ArrayList<Book> copy = new ArrayList<>();
        copy.addAll(list);
        Collections.sort(copy, new ProgressComparator());
        return new BookList(new ArrayList<>(copy));
    }


    /**
     * Gets a random book from the reading list
     * @return book data
     */
    public BookData getRandomBook() {
        if (list.size() == 0) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        return new BookData(list.get(index), index);
    }


    /**
     * Gets the index and list of a book
     * @param book book to find
     * @return book data
     */
    public BookData getIndex(Book book) {
        for (int i = 0; i < list.size(); i++) {
            if (book.equals(list.get(i))) {
                return new BookData(book, i, ReadingListConstants.SOURCE_MAIN);
            }
        }
        for (int i = 0; i < finishedList.size(); i++) {
            if (book.equals(finishedList.get(i))) {
                return new BookData(book, i, ReadingListConstants.SOURCE_FINISHED);
            }
        }
        return null;
    }


    /**
     * Clears the reading list
     */
    public void clearReading() {
        list.clear();
    }


    /**
     * Clears the finished list
     */
    public void clearFinished() {
        finishedList.clear();
    }


    /**
     * Clears the entire book list
     */
    public void clearAll() {
        clearReading();
        clearFinished();
    }

}
