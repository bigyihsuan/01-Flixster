package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.json_response.NowPlayingResponse;
import com.example.flixster.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + MOVIE_API_KEY;
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // create adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set adapter on recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new NowPlayingResponse(movies, movieAdapter));
    }
}