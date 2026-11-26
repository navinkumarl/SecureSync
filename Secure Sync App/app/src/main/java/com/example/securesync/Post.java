package com.example.securesync;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    @SerializedName("result")
    @Expose
    private boolean result;
    @SerializedName("data")
    @Expose
    private DataClass data;

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }

    public DataClass getData() {
        return data;
    }
    public void setData(DataClass data) {
        this.data = data;
    }

    public static class DataClass {
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("movies")
        @Expose
        private List<MoviesData> movies;

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public List<MoviesData> getMovies() {
            return movies;
        }
        public void setMovies(List<MoviesData> movies) {
            this.movies = movies;
        }
    }
}