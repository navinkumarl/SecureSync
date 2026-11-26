package com.example.securesync;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesData {
    @SerializedName("name")
    @Expose
    private final String name;
    @SerializedName("hero")
    @Expose
    private final String hero;

    public MoviesData(String movie_name, String movie_hero) {
        name = movie_name;
        hero = movie_hero;
    }

    public String getName() {
        return name;
    }

    public String getHero() {
        return hero;
    }
}
