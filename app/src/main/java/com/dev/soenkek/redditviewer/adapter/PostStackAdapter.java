package com.dev.soenkek.redditviewer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dev.soenkek.redditviewer.fragments.PostFragment;
import com.dev.soenkek.redditviewer.fragments.PostFragment.PostClickListener;
import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.models.Subreddit;

import java.util.ArrayList;

public class PostStackAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Post> mData;
    private PostClickListener mCallback;


    public PostStackAdapter(FragmentManager fragmentManager, PostClickListener mCallback) {
        super(fragmentManager);
        this.mCallback = mCallback;
    }

    @Override
    public Fragment getItem(int position) {
        PostFragment fragment = new PostFragment();
        fragment.setmCallback(mCallback);
        fragment.setmPost(mData.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void addData(ArrayList<Post> posts) {
        if (mData == null) mData = new ArrayList<>();
        for (Post post : posts) {
            this.mData.add(post);
        }
    }
}
