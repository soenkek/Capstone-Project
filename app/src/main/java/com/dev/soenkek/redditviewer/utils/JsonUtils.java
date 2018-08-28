package com.dev.soenkek.redditviewer.utils;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import com.dev.soenkek.redditviewer.models.Post;
import com.dev.soenkek.redditviewer.models.Subreddit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Post[] parsePostsJson(String json) throws JSONException {
        JSONArray arr = new JSONObject(json).getJSONObject("data").getJSONArray("children");
        Post[] posts = new Post[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            JSONObject data = arr.getJSONObject(i).getJSONObject("data");
            String subreddit = data.getString("subreddit");
            String title = data.getString("title");
//            String type = data.getString("type");
            String text = data.getString("selftext");
            String url = data.getString("url");
            String author = data.getString("author");
            String timeStamp = data.getString("created_utc");
            int score = data.getInt("score");
            int numComments = data.getInt("num_comments");
            posts[i] = new Post(null, title, null, text, url, author, timeStamp, score, numComments);
        }
        return posts;
    }

    public static Pair<Subreddit[], String> parseSearchJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
        String after = jsonObject.optString("after");
        JSONArray arr = jsonObject.getJSONArray("children");
        Subreddit[] subreddits = new Subreddit[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            JSONObject data = arr.getJSONObject(i).getJSONObject("data");
            String name = data.optString("url");
            String description = data.optString("public_description");
            String iconUrl = data.optString("icon_img");
            if (iconUrl == null || iconUrl.equals("")) {
                iconUrl = data.optString("community_icon");
            }
            int numSubscribers = data.optInt("subscribers");
            subreddits[i] = new Subreddit(name, description, iconUrl, numSubscribers);
        }
        return new Pair<>(subreddits, after);
    }
}
