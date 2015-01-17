package com.c4d3r.reddit.model;

/**
 * Created by Maxim on 17/01/2015.
 */
public class Topic
{
    private String domain;

    private String htmlText;

    private String author;

    private boolean stickied;

    private String title;

    private int createdOn;

    private int score;

    private String thumbnailUrl;

    public Topic(String title, String author, int score)
    {
        this.title = title;
        this.author = author;
        this.score = score;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isStickied() {
        return stickied;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(int createdOn) {
        this.createdOn = createdOn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
