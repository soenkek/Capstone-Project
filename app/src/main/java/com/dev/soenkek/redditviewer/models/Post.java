package com.dev.soenkek.redditviewer.models;

public class Post {

    private String subreddit;
    private String title;
    private String type;
    private String text;
    private String url;
    private String author;
    private String timestamp;
    private int score;
    private int comments;

    public Post(String subreddit, String title, String type, String text, String url, String author, String timestamp, int score, int comments) {
        this.subreddit = subreddit;
        this.title = title;
        this.type = type;
        this.text = text;
        this.url = url;
        this.author = author;
        this.timestamp = timestamp;
        this.score = score;
        this.comments = comments;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
