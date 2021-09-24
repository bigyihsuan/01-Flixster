package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.example.flixster.json_response.VideoResponse;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyBDbZftdBVAeo1oLLXb3SCP9K0uLkhGBXc";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=" + MainActivity.MOVIE_API_KEY;

    TextView tvDetailTitle;
    RatingBar ratingBar;
    TextView tvDetailOverview;
    static YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        ratingBar = findViewById(R.id.detailRatingBar);
        tvDetailOverview = findViewById(R.id.tvDetailOverview);
        youTubePlayerView = findViewById(R.id.detailPlayer);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        String title = movie.getTitle();
        String overview = movie.getOverview();
        double rating = movie.getVoteAverage();

        tvDetailTitle.setText(title);
        tvDetailOverview.setText(overview);
        ratingBar.setRating((float) rating);

        VideoResponse videoResponse = new VideoResponse(movie);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), videoResponse);
    }

    public static void initializeYoutube(final String youtubeKey, boolean isPopular) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("Youtube init", "onInitializationSuccess " + youtubeKey);
                youTubePlayer.cueVideo(youtubeKey);

                // stretch goal: autoplay popular movie videos
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {}

                    @Override
                    public void onLoaded(String videoId) {
                        if (isPopular) {
                            youTubePlayer.play();
                        }
                    }

                    @Override
                    public void onAdStarted() {}

                    @Override
                    public void onVideoStarted() {}

                    @Override
                    public void onVideoEnded() {}

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {}
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("Youtube init", "onInitializationFailure");
            }
        });
    }
}