package com.dev.soenkek.redditviewer.utils;

import android.os.AsyncTask;

import com.dev.soenkek.redditviewer.models.Comment;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchCommentsAsyncTask extends AsyncTask<URL, Void, ArrayList<Comment>> {

    private FetchCommentsResultListener mCallback;

    public interface FetchCommentsResultListener {
        void onCommentsRetrieved(ArrayList<Comment> comments);
    }

    public FetchCommentsAsyncTask(FetchCommentsResultListener mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected ArrayList<Comment> doInBackground(URL... urls) {
        ArrayList<Comment> comments = new ArrayList<>();
        String json = null;
        URL url = urls[0];
        int i = 0;
        try {
            json = NetworkUtils.getHttpUrlResponse(url);
            Comment[] newComments = JsonUtils.parseCommentsJson(json);
            for (Comment newComment : newComments) {
                comments.add(newComment);
                i++;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        mCallback.onCommentsRetrieved(comments);
    }
}
