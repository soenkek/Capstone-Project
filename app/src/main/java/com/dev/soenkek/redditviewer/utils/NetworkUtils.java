package com.dev.soenkek.redditviewer.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class NetworkUtils {

    public static final String BASE_URL = "https://www.reddit.com";

    public static URL buildPostUrl(String subreddit, int limit) throws MalformedURLException {
        URL url = null;
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
//                .appendPath("r")
                .appendEncodedPath(subreddit)
                .appendPath("hot.json")
                .appendQueryParameter("include_over_18", "0")
                .appendQueryParameter("limit", String.valueOf(limit))
                .build();
        url = new URL(buildUri.toString());
        return url;
    }

    public static URL buildSearchUrl(String query, int limit, String after) throws MalformedURLException, UnsupportedEncodingException {
        URL url = null;
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("search.json")
                .appendQueryParameter("q", query)
                .appendQueryParameter("type", "sr,user")
                .appendQueryParameter("include_over_18", "0")
                .appendQueryParameter("limit", String.valueOf(limit))
                .appendQueryParameter("after", after)
                .build();
        url = new URL(buildUri.toString());
        return url;
    }

    public static String getHttpUrlResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
