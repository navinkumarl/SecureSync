package com.example.securesync;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MoviesData> dataList;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView movie, hero;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie = itemView.findViewById(R.id.movies_name);
            hero = itemView.findViewById(R.id.movies_heros);
        }
    }

    public MovieAdapter(ArrayList<MoviesData> data) {
        dataList = data;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MoviesData moviesData = dataList.get(position);
        holder.movie.setText(moviesData.getName());
        holder.hero.setText(moviesData.getHero());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setMovieList(ArrayList<MoviesData> movieData) {
        dataList=movieData;
    }
}
