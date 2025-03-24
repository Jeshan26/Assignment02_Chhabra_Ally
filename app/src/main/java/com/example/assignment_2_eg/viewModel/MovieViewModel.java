package com.example.assignment_2_eg.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {



    private final MutableLiveData<List<MovieModel>> movieData = new MutableLiveData<>();

    public LiveData<List<MovieModel>> getMovieData(){
        return movieData;
    }

    public void SearchMovie(String movieName){

        String urlString = "https://www.omdbapi.com/?apikey=4aded2f&type=movie&s=" + movieName;

        ApiClient.getMovies(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String resData = response.body().string();

                JSONObject json = null;

                try{
//                    converting response string to JSONObject
                    json = new JSONObject(resData);

                    JSONArray searchArray = json.getJSONArray("Search");

                    List<MovieModel> movies = new ArrayList<>();

                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject movieJson = searchArray.getJSONObject(i);

                        MovieModel movie = new MovieModel();
                        movie.setTitle(movieJson.getString("Title"));
                        movie.setYear(movieJson.getString("Year"));
                        movie.setPoster(movieJson.getString("Poster"));
                        movie.setID(movieJson.getString("imdbID"));

                        movies.add(movie);
                    }

                    movieData.postValue(movies); // Post data to LiveData

                }catch(JSONException e){
                    throw new RuntimeException(e);

                }
            }
        });
    }

}
