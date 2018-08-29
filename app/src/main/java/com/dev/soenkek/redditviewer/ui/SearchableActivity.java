package com.dev.soenkek.redditviewer.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.adapter.SearchableAdapter;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.dev.soenkek.redditviewer.utils.FetchSubredditsAsyncTask;

public class SearchableActivity extends AppCompatActivity implements FetchSubredditsAsyncTask.FetchSubredditsResultListenener, SearchableAdapter.SubscribeClickListener {

    private RecyclerView recyclerView;

    private SearchableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent callingIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(callingIntent.getAction())) {
            recyclerView = findViewById(R.id.searchable_result_rv);
            mAdapter = new SearchableAdapter(this, recyclerView, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(mAdapter);

            String query = callingIntent.getStringExtra(SearchManager.QUERY);
            new FetchSubredditsAsyncTask(this).execute(query);
        }
    }

    @Override
    public void onSubredditsRetrieved(Subreddit[] subreddits) {
        mAdapter.addData(subreddits);
    }

    @Override
    public void onSubscribe(Subreddit subreddit) {
        Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnsubscribe(Subreddit subreddit) {
        Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT).show();
    }
}
