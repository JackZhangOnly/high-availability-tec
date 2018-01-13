package com.jackzhang.bootes.model;

/**
 * Created by Jack
 */
public class BookQueryCondition {
    private String title;
    private String author;
    private int gt_word_count;
    private int lt_word_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getGt_word_count() {
        return gt_word_count;
    }

    public void setGt_word_count(int gt_word_count) {
        this.gt_word_count = gt_word_count;
    }

    public int getLt_word_count() {
        return lt_word_count;
    }

    public void setLt_word_count(int lt_word_count) {
        this.lt_word_count = lt_word_count;
    }
}
