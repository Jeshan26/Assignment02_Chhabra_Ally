package com.example.assignment_2_eg.viewModel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailsViewModel extends ViewModel {

    MovieModel movie;



    private final MutableLiveData<MovieModel> movieData = new MutableLiveData<>();

    public LiveData<MovieModel> getMovieDetailsData(){
        return movieData;
    }

    public void getMovieDetails(String movieId){
        String urlString = "https://www.omdbapi.com/?apikey=4aded2f&i=" + movieId;

        ApiClient.getMovies(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
             int i = 10;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String resData = response.body().string();

                JSONObject json = null;

                try {
//                    converting response string to JSONObject
                    json = new JSONObject(resData);
                    movie = new MovieModel(); //new Model object to keep the movie data

                    // Extracting the movie's data from the JSON response
                    String strTitle = json.getString("Title");
                    String strYear = json.getString("Year");
                    String strGenre = json.getString("Genre");
                    String strRating = json.getString("imdbRating");
                    String strPoster = json.getString("Poster");
                    String strRuntime = json.getString("Runtime");
                    String strPlot = json.getString("Plot");
                    String strLanguage = json.getString("Language");
                    String strRated = json.getString("Rated");

//                  setting the movies data into the model object
                    movie.setTitle(strTitle);
                    movie.setYear(strYear);
                    movie.setRating(strRating);
                    movie.setRuntime(strRuntime);
                    movie.setGenre(strGenre);
                    movie.setStudio(strPlot);
                    movie.setPoster(strPoster);
                    movie.setLanguage(strLanguage);
                    movie.setRated(strRated);

                    movieData.postValue(movie); // for the UI to update with the information

                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
