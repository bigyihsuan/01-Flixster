package com.example.flixster.json_response;

import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.DetailActivity;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class VideoResponse extends JsonHttpResponseHandler {

    Movie movie;
    String youtubeKey;

    public VideoResponse(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onSuccess(int statusCode, Headers headers, JSON json) {
        JSONObject jsonObject = json.jsonObject;
        try {
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() == 0) {
                return;
            }
            this.youtubeKey = results.getJSONObject(0).getString("key");
            Log.d("VideoResponse", "Youtube Key = " + this.youtubeKey);
            DetailActivity.initializeYoutube(this.getYoutubeKey(), movie.isPopular());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
    }

    public String getYoutubeKey() {
        return youtubeKey;
    }
}
