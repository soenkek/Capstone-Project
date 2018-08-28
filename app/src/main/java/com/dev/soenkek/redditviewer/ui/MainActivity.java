package com.dev.soenkek.redditviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.adapter.PostStackAdapter;
import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.transformer.PostStackTransformer;
import com.dev.soenkek.redditviewer.utils.FetchPostsAsyncTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchPostsAsyncTask.FetchPostsResultListener {

    private ViewPager mPager;

    private PostStackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FIXME app crash on rotation change
        mPager = findViewById(R.id.main_view_pager);
        mAdapter = new PostStackAdapter(getSupportFragmentManager());
        mPager.setPageTransformer(true, new PostStackTransformer());
        mPager.setOffscreenPageLimit(3);
        new FetchPostsAsyncTask(this).execute(new String[]{"Android", "GetMotivated"});
    }

    @Override
    public void onPostsRetrieved(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            mAdapter.setmData(posts);
            mPager.setAdapter(mAdapter);
        } else {
//            TODO display error message
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context =  this;
        switch (item.getItemId()) {
            case R.id.action_manage_subscriptions:
                startActivity(new Intent(context, SubscriptionsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
