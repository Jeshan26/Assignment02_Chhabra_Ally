package com.example.assignment_2_eg.model;

public class MovieModel {
    
    // Declare variables to store movie information

    private String MovieId;
    private String Title;
    private String Studio;

    private String Language;

    private String Rated;


    private String Rating;
    private String Year;

    private String Poster;

    private String Runtime;

    private String Genre;

    //Getters and Setters forthe movie informations

    public MovieModel(){

    }

    public String getID() {
        return MovieId;
    }

    public void setID(String id) {
        MovieId = id;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStudio() {
        return Studio;
    }

    public void setStudio(String studio) {
        Studio = studio;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }
}
