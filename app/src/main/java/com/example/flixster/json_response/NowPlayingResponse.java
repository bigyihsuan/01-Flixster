package com.example.flixster.json_response;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class NowPlayingResponse extends JsonHttpResponseHandler {
    List<Movie> movies;
    MovieAdapter movieAdapter;
    public NowPlayingResponse(List<Movie> movies, MovieAdapter movieAdapter) {
        this.movies = movies;
        this.movieAdapter = movieAdapter;
    }

    @Override
    public void onSuccess(int statusCode, Headers headers, JSON json) {
        JSONObject jsonObject = json.jsonObject;
        try {
            JSONArray results = jsonObject.getJSONArray("results");
            movies.addAll(Movie.fromJsonArray(results));
            movieAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
    }
}
