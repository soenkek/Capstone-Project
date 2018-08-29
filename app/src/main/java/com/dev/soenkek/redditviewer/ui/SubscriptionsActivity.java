package com.dev.soenkek.redditviewer.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.adapter.SubscriptionsAdapter;
import com.dev.soenkek.redditviewer.data.DbContract;
import com.dev.soenkek.redditviewer.models.Subreddit;

import java.util.ArrayList;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionsAdapter.UnsubscribeListener {

    private SubscriptionsAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.subscriptions_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Uri uri = DbContract.Subscriptions.CONTENT_URI;
        //        TODO db operations in background thread
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);

        mAdapter = new SubscriptionsAdapter(this, this);
        ArrayList<Subreddit> subreddits = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(cursor.getColumnIndex(DbContract.Subscriptions.COLUMN_NAME));
                String iconUrl= cursor.getString(cursor.getColumnIndex(DbContract.Subscriptions.COLUMN_ICON_URL));
                subreddits.add(new Subreddit(name, null, iconUrl, 0));
                cursor.moveToNext();
            }
        }
        mAdapter.setmData(subreddits);
        recyclerView.setAdapter(mAdapter);
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subscriptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUnsubscribe(Subreddit subreddit) {
        Uri uri = DbContract.Subscriptions.CONTENT_URI.buildUpon().appendPath(subreddit.getName()).build();
        this.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void onSubscribe(Subreddit subreddit) {
        Uri uri = DbContract.Subscriptions.CONTENT_URI;
        ContentValues cv = new ContentValues();
        cv.put(DbContract.Subscriptions.COLUMN_NAME, subreddit.getName());
        cv.put(DbContract.Subscriptions.COLUMN_ICON_URL, subreddit.getIconUrl());
        this.getContentResolver().insert(uri, cv);
    }
}
