package com.dev.soenkek.redditviewer.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String AUTHORITY = "com.dev.soenkek.redditviewer";
    public static final String PATH_SUBSCRIPTIONS = "subscriptions";
    public static final String PATH_POSTS = "posts";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Subscriptions implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBSCRIPTIONS).build();
        public static final String TABLE_NAME = "subscriptions";
        public static final String COLUMN_NAME = "subscriptionName";
        public static final String COLUMN_ICON_URL = "subscriptionIconUrl";
    }

    public static final class Posts implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSTS).build();
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_SUBREDDIT = "subreddit";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_COMMENTS = "numComments";
    }
}
