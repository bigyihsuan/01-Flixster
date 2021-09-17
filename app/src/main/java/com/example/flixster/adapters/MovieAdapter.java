package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    private final int BAD_MOVIE = 0, GOOD_MOVIE = 1;
    private final double RATING_THRESHOLD = 5d;

    public MovieAdapter(Context context, List<com.example.flixster.models.Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // for hetero
    // if rating of the movie >= 5, return landscape
    @Override
    public int getItemViewType(int position) {
        Movie movie = movies.get(position);
        return movie.getVoteAverage() >= RATING_THRESHOLD ? GOOD_MOVIE : BAD_MOVIE;
    }

    // involves inflating a layout from xml and returning the holder
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");

        MovieAdapter.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case GOOD_MOVIE: {
                // use the backdrop
                View goodMovie = inflater.inflate(R.layout.item_movie_backdrop, parent, false);
                viewHolder = new ViewHolderBackdrop(goodMovie);
                break;
            }
            case BAD_MOVIE:
            default: {
                // use the poster
                View badMovie = inflater.inflate(R.layout.item_movie_poster, parent, false);
                viewHolder = new ViewHolderPoster(badMovie);
                break;
            }
        }

        return viewHolder;
    }

    // populating the data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position + " rating " + movies.get(position).getVoteAverage());
        switch (holder.getItemViewType()) {
            case GOOD_MOVIE: {
                ViewHolderBackdrop viewHolderBackdrop = (ViewHolderBackdrop) holder;
                configureViewHolderBackdrop(viewHolderBackdrop, position);
                break;
            }
            case BAD_MOVIE:
            default: {
                ViewHolderPoster viewHolderPoster = (ViewHolderPoster) holder;
                configureViewHolderPoster(viewHolderPoster, position);
                break;
            }
        }
    }

    private void configureViewHolderBackdrop(ViewHolderBackdrop vhb, int position) {
        Movie movie = movies.get(position);
        vhb.bind(movie);
    }

    private void configureViewHolderPoster(ViewHolderPoster vhp, int position) {
        Movie movie = movies.get(position);
        vhp.bind(movie);
    }

    // return the number of items in the list
    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderPoster extends MovieAdapter.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolderPoster(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;

            // if in landscape, imageUrl = backdrop
            // else, imageUrl = poster

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);

        }
    }

    public class ViewHolderBackdrop extends MovieAdapter.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolderBackdrop(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;

            // if in landscape, imageUrl = backdrop
            // else, imageUrl = poster

            imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).into(ivPoster);

        }
    }


}
