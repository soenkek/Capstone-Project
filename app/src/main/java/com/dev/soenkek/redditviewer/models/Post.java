package com.dev.soenkek.redditviewer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

    private String postId;
    private String subreddit;
    private String title;
    private String type;
    private String text;
    private String url;
    private String author;
    private String timestamp;
    private int score;
    private int comments;

    public Post(String postId, String subreddit, String title, String type, String text, String url, String author, String timestamp, int score, int comments) {
        this.postId = postId;
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

    protected Post(Parcel in) {
        postId = in.readString();
        subreddit = in.readString();
        title = in.readString();
        type = in.readString();
        text = in.readString();
        url = in.readString();
        author = in.readString();
        timestamp = in.readString();
        score = in.readInt();
        comments = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(subreddit);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(text);
        dest.writeString(url);
        dest.writeString(author);
        dest.writeString(timestamp);
        dest.writeInt(score);
        dest.writeInt(comments);
    }
}
