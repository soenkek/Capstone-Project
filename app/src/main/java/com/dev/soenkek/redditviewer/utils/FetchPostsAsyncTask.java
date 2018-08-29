package com.dev.soenkek.redditviewer.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.dev.soenkek.redditviewer.models.Post;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchPostsAsyncTask extends AsyncTask<String, Void, ArrayList<Post>> {

    private FetchPostsResultListener mCallback;

    public interface FetchPostsResultListener {
        void onPostsRetrieved(ArrayList<Post> posts);
    }

    public FetchPostsAsyncTask(FetchPostsResultListener mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected ArrayList<Post> doInBackground(String... strings) {
        ArrayList<Post> posts = new ArrayList<>();
        String json = null;
        URL url = null;
        int i = 0;
        int limit = (10 / strings.length) + 1;
        for (String subreddit : strings) {
            try {
                url = NetworkUtils.buildPostUrl(subreddit, limit);
                json = NetworkUtils.getHttpUrlResponse(url);
                Post[] newPosts = JsonUtils.parsePostsJson(json);
                for (Post newPost : newPosts) {
                    posts.add(newPost);
                    i++;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        TODO randomize posts?
        return posts;
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        mCallback.onPostsRetrieved(posts);
    }
}
