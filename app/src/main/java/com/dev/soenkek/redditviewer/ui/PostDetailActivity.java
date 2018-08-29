package com.dev.soenkek.redditviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.dev.soenkek.redditviewer.utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import static com.dev.soenkek.redditviewer.ui.MainActivity.EXTRA_SUBREDDIT;

public class PostDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POST_URL = "extraPostUrl";
    private Post mData;
    private Context mContext;

    private TextView scoreTv;
    private TextView authorTv;
    private TextView timeStampTv;
    private TextView titleTv;
    private TextView textTv;
    private ImageView imageIv;
    private TextView commentsTv;
    private View commentsV;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.hasExtra(EXTRA_SUBREDDIT)) {
            mData = callingIntent.getExtras().getParcelable(EXTRA_SUBREDDIT);
        }

        mContext = this;

        scoreTv = findViewById(R.id.post_score);
        authorTv = findViewById(R.id.post_author);
        timeStampTv = findViewById(R.id.post_time_stamp);
        titleTv = findViewById(R.id.post_title);
        textTv = findViewById(R.id.post_text);
        imageIv = findViewById(R.id.post_image);
        commentsTv = findViewById(R.id.post_comments_label);
        commentsV = findViewById(R.id.post_comments);

        String title = mData.getTitle();
        String text = mData.getText();
        String url = mData.getUrl();
        String author = mData.getAuthor();
        String timeStamp = mData.getTimestamp();
        int score = mData.getScore();
        int numComments = mData.getComments();
        int stringRes;
        if (numComments == 1) stringRes = R.string.label_comments_singular;
        else stringRes = R.string.label_comments_plural;
        String comments = numComments + " " + getResources().getString(stringRes);

        scoreTv.setText(String.valueOf(score));
        authorTv.setText(author);
        timeStampTv.setText(timeStamp);
        titleTv.setText(title);
//        TODO distinguish between text/image/video/link/discussion
        textTv.setText(text);
        if (true) {
            imageIv.setVisibility(View.GONE);
        } else {
            imageIv.setVisibility(View.VISIBLE);
            Picasso.get().load(url).into(imageIv);
        }
        commentsTv.setText(comments);

        String subredditName = "/r/" + mData.getSubreddit();
        try {
            final URL postUrl = NetworkUtils.buildCommentsUrl(subredditName, mData.getPostId());
            commentsV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, CommentsPreviewActivity.class).putExtra(EXTRA_POST_URL, postUrl.toString()));
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !mData.getSubreddit().equals("")) {
            actionBar.setTitle(subredditName);        }
    }
}
