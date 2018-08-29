package com.dev.soenkek.redditviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.adapter.PostStackAdapter;
import com.dev.soenkek.redditviewer.data.DbContract;
import com.dev.soenkek.redditviewer.fragments.PostFragment;
import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.dev.soenkek.redditviewer.transformer.PostStackTransformer;
import com.dev.soenkek.redditviewer.utils.FetchPostsAsyncTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchPostsAsyncTask.FetchPostsResultListener, PostFragment.PostClickListener {

    public static final String EXTRA_SUBREDDIT = "extraSubreddit";
    public static final String ADMOB_APP_ID_TEST = "ca-app-pub-3940256099942544/6300978111";
    private ViewPager mPager;
    private AdView mAdView;

    private PostStackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, ADMOB_APP_ID_TEST);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

//        FIXME app crash on rotation change
        mPager = findViewById(R.id.main_view_pager);
        mAdapter = new PostStackAdapter(getSupportFragmentManager(), this);
        mPager.setPageTransformer(true, new PostStackTransformer());
        mPager.setOffscreenPageLimit(3);

        Uri uri = DbContract.Subscriptions.CONTENT_URI;
        //        TODO db operations in background thread
        Cursor cursor = this.getContentResolver().query(uri, new String[]{DbContract.Subscriptions.COLUMN_NAME}, null, null, null);
        if (cursor != null) {
            String[] subreddits =  new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                subreddits[i] = cursor.getString(cursor.getColumnIndex(DbContract.Subscriptions.COLUMN_NAME));
                cursor.moveToNext();
            }
            if (subreddits.length > 0) {
                new FetchPostsAsyncTask(this).execute(subreddits);
            } else {
//                TODO display message: no subscriptions yet
            }
        }
        cursor.close();
    }

    @Override
    public void onPostsRetrieved(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            mAdapter.addData(posts);
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
            case R.id.action_settings:
                startActivity(new Intent(context, SubscriptionsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostClicked(Post post) {
        startActivity(new Intent(this, PostDetailActivity.class).putExtra(EXTRA_SUBREDDIT, post));
    }
}
