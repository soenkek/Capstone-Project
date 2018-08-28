package com.dev.soenkek.redditviewer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.models.Post;

public class PostFragment extends Fragment {

    private Post mPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String title = mPost.getTitle();
        String text = mPost.getText();
        String url = mPost.getUrl();
        String author = mPost.getAuthor();
        String timeStamp = mPost.getTimestamp();
        int score = mPost.getScore();
        int numComments = mPost.getComments();
        int stringRes;
        if (numComments == 1) stringRes = R.string.label_comments_singular;
        else stringRes = R.string.label_comments_plural;
        String comments = numComments + " " + getResources().getString(stringRes);

        View rootView = inflater.inflate(R.layout.card_view_post, container, false);

        TextView scoreTv = rootView.findViewById(R.id.post_score);
        scoreTv.setText(String.valueOf(score));
        TextView authorTv = rootView.findViewById(R.id.post_author);
        authorTv.setText(author);
        TextView timeStampTv = rootView.findViewById(R.id.post_time_stamp);
        timeStampTv.setText(timeStamp);
        TextView titleTv = rootView.findViewById(R.id.post_title);
        titleTv.setText(title);
//        TODO distinguish between text/image/video/link/discussion
        TextView textTv = rootView.findViewById(R.id.post_text);
        textTv.setText(text);
        ImageView imageIv = rootView.findViewById(R.id.post_image);
        if (true) {
            imageIv.setVisibility(View.GONE);
        } else {
            imageIv.setVisibility(View.VISIBLE);
        }
        TextView commentsTv = rootView.findViewById(R.id.post_comments_label);
        commentsTv.setText(comments);

        return rootView;
    }

    public void setmPost(Post mPost) {
        this.mPost = mPost;
    }
}
