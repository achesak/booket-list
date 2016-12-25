package com.chesak.adam.readinglist;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a book in the reading list. Some fields may not be used yet.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public class Book implements Serializable {

    private String title;
    private String author;
    private int pageCount;
    private int pageRead;
    private boolean finishedReading;
    private String imageUrl;
    private String synopsis;
    private float rating;
    private float userRating;
    private String publishDate;
    private String isbn;
    private Date startedReading;
    private String publisher;

    /**
     * Constructor: default
     */
    public Book() {
        this.title = "";
        this.author = "";
        this.pageCount = 0;
        this.pageRead = 0;
        this.finishedReading = false;
        this.imageUrl = "";
        this.synopsis = "";
        this.rating = 0;
        this.userRating = 0;
        this.publishDate = "";
        this.isbn = "";
        this.startedReading = new Date();
    }


    /**
     * Constructor
     * @param title title
     * @param author author
     */
    public Book(String title, String author) {
        this();
        this.title = title;
        this.author = author;
    }


    /**
     * Constructor
     * @param title title
     * @param author author
     * @param pageCount page count
     */
    public Book(String title, String author, int pageCount) {
        this(title, author);
        this.pageCount = pageCount;
    }


    /**
     * Constructor
     * @param title title
     * @param author author
     * @param pageCount page count
     * @param isbn ISBN
     */
    public Book(String title, String author, int pageCount, String isbn) {
        this(title, author, pageCount);
        this.isbn = isbn;
    }


    /**
     * Constructor
     * @param title title
     * @param author author
     * @param pageCount page count
     * @param isbn ISBN
     * @param publisher publisher
     * @param publishDate publish date
     */
    public Book(String title, String author, int pageCount, String isbn, String publisher, String publishDate) {
        this(title, author, pageCount, isbn);
        this.publisher = publisher;
        this.publishDate = publishDate;
    }


    /**
     * Gets the title
     * @return title
     */
    public String getTitle() {
        return title;
    }


    /**
     * Sets the title
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Gets the author
     * @return author
     */
    public String getAuthor() {
        return author;
    }


    /**
     * Sets the author
     * @param author author
     */
    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * Gets the page count
     * @return page count
     */
    public int getPageCount() {
        return pageCount;
    }


    /**
     * Sets the page count
     * @param pageCount page count
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        if (pageRead >= pageCount) {
            setFinishedReading(true);
        }
    }


    /**
     * Gets the number of pages read
     * @return number of pages read
     */
    public int getPageRead() {
        return pageRead;
    }


    /**
     * Sets the number of pages read
     * @param pageRead number of pages read
     */
    public void setPageRead(int pageRead) {
        if (pageRead > pageCount) {
            pageRead = pageCount;
            setFinishedReading(true);
        }
        this.pageRead = pageRead;
    }


    /**
     * Gets if the book is done
     * @return true if done reading, false otherwise
     */
    public boolean isFinishedReading() {
        return finishedReading;
    }


    /**
     * Sets if the book is done
     * @param finishedReading true if done reading, false otherwise
     */
    public void setFinishedReading(boolean finishedReading) {
        this.finishedReading = finishedReading;
    }


    /**
     * Gets the cover image URL
     * @return cover image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }


    /**
     * Sets the cover image URL
     * @param imageUrl cover image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    /**
     * Gets the synopsis
     * @return synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }


    /**
     * Sets the synopsis
     * @param synopsis synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }


    /**
     * Gets the rating
     * @return rating
     */
    public float getRating() {
        return rating;
    }


    /**
     * Sets the rating
     * @param rating rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }


    /**
     * Gets the user rating
     * @return user rating
     */
    public float getUserRating() {
        return userRating;
    }


    /**
     * Sets the user rating
     * @param userRating user rating
     */
    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }


    /**
     * Gets the publish date
     * @return publish date
     */
    public String getPublishDate() {
        return publishDate;
    }


    /**
     * Sets the publish date
     * @param publishDate publish date
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }


    /**
     * Gets the ISBN
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }


    /**
     * Sets the ISBN
     * @param isbn ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    /**
     * Gets the date reading started
     * @return start date
     */
    public Date getStartedReading() {
        return startedReading;
    }


    /**
     * Sets te date reading started
     * @param startedReading start date
     */
    public void setStartedReading(Date startedReading) {
        this.startedReading = startedReading;
    }


    /**
     * Gets the publisher
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }


    /**
     * Sets the publisher
     * @param publisher publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
