package com.dev.soenkek.redditviewer.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.dev.soenkek.redditviewer.ui.SearchableActivity;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class FetchSubredditsAsyncTask extends AsyncTask<String, Subreddit, Void> {

    private FetchSubredditsResultListenener mCallback;

    public FetchSubredditsAsyncTask(FetchSubredditsResultListenener mCallback) {
        this.mCallback = mCallback;
    }

    public interface FetchSubredditsResultListenener {
        void onSubredditsRetrieved(Subreddit[] subreddits);
    }

    @Override
    protected Void doInBackground(String... strings) {
        String json = null;
        URL url = null;
        ArrayList<Subreddit> subreddits = new ArrayList<>();
        String after = new String();
        try {
            do {
                url = NetworkUtils.buildSearchUrl(strings[0], 5, after);
                json = NetworkUtils.getHttpUrlResponse(url);
                Pair<Subreddit[], String> result = JsonUtils.parseSearchJson(json);
                Subreddit[] newSubreddits = result.first;
                after = result.second;
                publishProgress(newSubreddits);
            } while (after != null && !after.equals("null"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Subreddit... values) {
        mCallback.onSubredditsRetrieved(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
