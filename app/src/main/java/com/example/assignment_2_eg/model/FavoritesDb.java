package com.example.assignment_2_eg.model;

import java.util.List;

public class FavoritesDb {
    private List<MovieModel> favMoviesList;

    public FavoritesDb() {

    }

    // Constructor
    public FavoritesDb(List<MovieModel> favMoviesList) {
        this.favMoviesList = favMoviesList;
    }

    // Getter and Setter for favMovies
    public List<MovieModel> getFavMoviesList() {
        return favMoviesList;
    }

    public void setFavMoviesList(List<MovieModel> favMoviesList) {
        this.favMoviesList = favMoviesList;
    }
}
