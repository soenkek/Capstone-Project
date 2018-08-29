package com.dev.soenkek.redditviewer.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProvider extends android.content.ContentProvider {

    private static final int SUBSCRIPTIONS = 100;
    private static final int SUBSCRIPTIONS_WITH_NAME = 101;
    private static final int POSTS = 200;
    private static final int POSTS_WITH_ID = 201;

    private Context mContext;
    private DbHelper dbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_SUBSCRIPTIONS + "", SUBSCRIPTIONS);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_SUBSCRIPTIONS + "/*", SUBSCRIPTIONS_WITH_NAME);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_POSTS + "", POSTS);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_POSTS + "/#", POSTS_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        dbHelper = new DbHelper(mContext);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String orderBy) {
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case SUBSCRIPTIONS:
                cursor = database.query(DbContract.Subscriptions.TABLE_NAME,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        orderBy);
                break;
            case SUBSCRIPTIONS_WITH_NAME:
                String name = uri.getPathSegments().get(1);
                cursor = database.query(DbContract.Subscriptions.TABLE_NAME,
                        null,
                        DbContract.Subscriptions.COLUMN_NAME + "=?",
                        new String[]{name},
                        null,
                        null,
                        null);
                break;
            case POSTS:
                cursor = database.query(DbContract.Posts.TABLE_NAME,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        orderBy);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        cursor.setNotificationUri(mContext.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long id;
        switch (match) {
            case SUBSCRIPTIONS:
                id = database.insert(DbContract.Subscriptions.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(DbContract.Subscriptions.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
            case POSTS:
                id = database.insert(DbContract.Posts.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(DbContract.Posts.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int deletedRows;
        switch (match) {
            case SUBSCRIPTIONS_WITH_NAME:
                String name = uri.getPathSegments().get(1);
                deletedRows = database.delete(DbContract.Subscriptions.TABLE_NAME, DbContract.Subscriptions.COLUMN_NAME + "=?", new String[]{name});
                if (deletedRows > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                }
                return deletedRows;
            case POSTS:
                deletedRows = database.delete(DbContract.Subscriptions.TABLE_NAME, null, null);
                if (deletedRows > 0) {
                    mContext.getContentResolver().notifyChange(uri, null);
                }
                return deletedRows;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBSCRIPTIONS:
                break;
            case SUBSCRIPTIONS_WITH_NAME:
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        mContext.getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
