package com.c4d3r.reddit.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Maxim on 17/01/2015.
 */
//TODO: Move to parcelable
public class Topic implements Serializable
{
    @SerializedName("domain")
    private String domain;

    @SerializedName("selftext_html")
    private String htmlText;

    @SerializedName("author")
    private String author;

    @SerializedName("stickied")
    private boolean stickied;

    @SerializedName("title")
    private String title;

    @SerializedName("created_utc")
    private int createdOn;

    @SerializedName("score")
    private int score;

    @SerializedName("thumbnail")
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
