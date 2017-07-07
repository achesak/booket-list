package com.chesak.adam.readinglist.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a book in the reading list. Some fields may not be used yet.
 *
 * @author Adam Chesak, achesak@yahoo.com
 */
public final class Book implements Serializable {

    private String title;
    private String author;
    private int pageCount;
    private int pageRead;
    private String imageUrl;
    private String thumbnailUrl;
    private String synopsis;
    private float userRating;
    private String publishDate;
    private String isbn;
    private Date startedReading;
    private String publisher;
    private String subtitle;

    /**
     * Constructor: default
     */
    public Book() {
        this.title = "";
        this.author = "";
        this.pageCount = 0;
        this.pageRead = 0;
        this.imageUrl = "";
        this.thumbnailUrl = "";
        this.synopsis = "";
        this.userRating = 0;
        this.publishDate = "";
        this.isbn = "";
        this.startedReading = new Date();
        this.subtitle = "";
        this.publisher = "";
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
     * Checks if the tag included in the book description should be displayed
     * @param tag tag
     * @return true if allowed, false otherwise
     */
    public static boolean checkTagAllowed(String tag) {
        tag = tag.toLowerCase();
        return !(tag.equals("in library") || tag.equals("accessible book") || tag.equals("protected daisy") || tag.contains("ebook") ||
                 tag.contains("e-book") || tag.equals("overdrive"));
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
        }
        this.pageRead = pageRead;
    }


    /**
     * Gets the percentage read
     * @return percent read
     */
    public int getProgress() {
        if (pageCount == 0) {
            return 0;
        }
        return (int) (((float) pageRead / pageCount) * 100);
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


    public String getStartedReadingFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return format.format(this.getStartedReading());
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


    /**
     * Gets the thumbnail URL
     * @return URL
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    /**
     * Sets the thumbnail URL
     * @param thumbnailUrl URL
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    /**
     * Checks if the book is read
     * @return true if done, false otherwise
     */
    public boolean isFinishedReading() {
        return pageRead >= pageCount;
    }


    /**
     * Gets the subtitle
     * @return subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }


    /**
     * Sets the subtitle
     * @param subtitle subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
