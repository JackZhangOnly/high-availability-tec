package com.jackzhang.bootes.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Jack
 */
public class Book {
    private String id;
    private String title;
    private String author;
    private int wordCount;
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date publicDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }
}
