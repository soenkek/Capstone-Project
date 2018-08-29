package com.dev.soenkek.redditviewer.models;

public class Comment {

    private String id;
    private String author;
    private int score;
    private String timestamp;
    private String text;
    private Comment[] replies;

    public Comment(String id, String author, int score, String timestamp, String text, Comment[] replies) {
        this.id = id;
        this.author = author;
        this.score = score;
        this.timestamp = timestamp;
        this.text = text;
        this.replies = replies;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment[] getReplies() {
        return replies;
    }

    public void setReplies(Comment[] replies) {
        this.replies = replies;
    }
}
