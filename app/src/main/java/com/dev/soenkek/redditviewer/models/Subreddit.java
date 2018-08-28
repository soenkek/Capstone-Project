package com.dev.soenkek.redditviewer.models;

public class Subreddit {

    private String name;
    private String description;
    private String iconUrl;
    private int numSubscribers;

    public Subreddit(String name, String description, String iconUrl, int numSubscribers){
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.numSubscribers = numSubscribers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getNumSubscribers() {
        return numSubscribers;
    }

    public void setNumSubscribers(int numSubscribers) {
        this.numSubscribers = numSubscribers;
    }
}
