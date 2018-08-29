package com.dev.soenkek.redditviewer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dev.soenkek.redditviewer.data.DbContract.*;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "simpleviewerforreddit.db";
    public static final int DATABSE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SUBSCRIPTIONS_TABLE = "CREATE TABLE " + Subscriptions.TABLE_NAME + " (" +
                Subscriptions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Subscriptions.COLUMN_NAME + " TEXT NOT NULL UNIQUE, " +
                Subscriptions.COLUMN_ICON_URL + " TEXT " +
                ");";
        final String SQL_CREATE_POSTS_TABLE = "CREATE TABLE " + Posts.TABLE_NAME + " (" +
                Posts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Posts.COLUMN_SUBREDDIT + " TEXT, " +
                Posts.COLUMN_TITLE + " TEXT, " +
                Posts.COLUMN_TYPE + " TEXT, " +
                Posts.COLUMN_TEXT + " TEXT, " +
                Posts.COLUMN_URL + " TEXT, " +
                Posts.COLUMN_AUTHOR + " TEXT, " +
                Posts.COLUMN_TIMESTAMP + " TEXT, " +
                Posts.COLUMN_SCORE + " INTEGER, " +
                Posts.COLUMN_COMMENTS + " INTEGER " +
                ");";
        db.execSQL(SQL_CREATE_SUBSCRIPTIONS_TABLE);
        db.execSQL(SQL_CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
