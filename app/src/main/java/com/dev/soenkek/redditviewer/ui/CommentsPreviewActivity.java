package com.dev.soenkek.redditviewer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.adapter.CommentsPreviewAdapter;
import com.dev.soenkek.redditviewer.models.Comment;
import com.dev.soenkek.redditviewer.utils.FetchCommentsAsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.dev.soenkek.redditviewer.ui.PostDetailActivity.EXTRA_POST_URL;

public class CommentsPreviewActivity extends AppCompatActivity implements FetchCommentsAsyncTask.FetchCommentsResultListener {

    private URL mUrl;
    private RecyclerView recyclerView;
    private CommentsPreviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_preview);

        recyclerView = findViewById(R.id.comments_preview_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CommentsPreviewAdapter();
        recyclerView.setAdapter(mAdapter);

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.hasExtra(EXTRA_POST_URL)) {
            try {
                mUrl = new URL(callingIntent.getExtras().getString(EXTRA_POST_URL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            new FetchCommentsAsyncTask(this).execute(mUrl);
        }
    }

    @Override
    public void onCommentsRetrieved(ArrayList<Comment> comments) {
        mAdapter.setmData(comments);
    }
}
