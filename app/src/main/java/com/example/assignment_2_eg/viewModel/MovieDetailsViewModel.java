package com.example.assignment_2_eg.viewModel;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment_2_eg.model.FavoritesDb;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailsViewModel extends ViewModel {

    //    Cloud firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get a reference to the 'FavMovies' collection
    CollectionReference collectionReference = db.collection("FavMovies");
    //   Getting the userid of the logged in user to add the data for user specific
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = collectionReference.document(userId);

    MovieModel movie = new MovieModel();


    private final MutableLiveData<MovieModel> movieData = new MutableLiveData<>();

    public LiveData<MovieModel> getMovieDetailsData() {
        return movieData;
    }

    //    this will be used to show toast msg when the movie is added to favorites
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }


    public void getMovieDetails(String movieId) {
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
//                    movie = new MovieModel(); //new Model object to keep the movie data

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
                    movie.setID(movieId);

                    movieData.postValue(movie); // for the UI to update with the information


                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void saveDataToDb() {
//        using  lamdas(arrow function instead of traditional class calling for better readability)
        documentReference.get().addOnSuccessListener(documentSnapshot -> {

            if (documentSnapshot.exists()) {
//                firebase stores as key value pairs(map) so we have to get the data and add to movieModel list for comparing
//                Firestore stores objects as a list of Map<String, Object>)
                List<Map<String, Object>> responseList = (List<Map<String, Object>>) documentSnapshot.get("favMoviesList");
                List<MovieModel> favMoviesList = new ArrayList<>();

                if (responseList != null) {
                    for (Map<String, Object> map : responseList) {
                        MovieModel movie = new MovieModel();
                        movie.setTitle((String) map.get("title"));
                        movie.setYear((String) map.get("year"));
                        movie.setGenre((String) map.get("genre"));
                        movie.setRating((String) map.get("rating"));
                        movie.setRuntime((String) map.get("runtime"));
                        movie.setStudio((String) map.get("studio"));
                        movie.setPoster((String) map.get("poster"));
                        movie.setLanguage((String) map.get("language"));
                        movie.setRated((String) map.get("rated"));
                        favMoviesList.add(movie);
                    }
                }

                if (favMoviesList == null) {
                    favMoviesList = new ArrayList<>();
                }

                // Check if movie already exists by title
                boolean movieAlreadyExists = false;
                for (MovieModel m : favMoviesList) {
                    if (m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                        movieAlreadyExists = true;
                        break;
                    }
                }

                if (!movieAlreadyExists) {
                    favMoviesList.add(movie);
                    documentReference.update("favMoviesList", favMoviesList)
                            .addOnSuccessListener(success -> {
//                            fetching context from the click
                                toastMessage.setValue("Movie added to favorites!");
                            })
                            .addOnFailureListener(error -> {
                                toastMessage.setValue("Cannot add the movie");
                                Log.d("tag", "Cannot add movie" + error);
                            });
                } else {
                    toastMessage.setValue("Movie already in the Favorites");
                }
            } else {
                // if document doesnot exist then create new one using set
                List<MovieModel> newlyAddedFavMovies = new ArrayList<>();
                newlyAddedFavMovies.add(movie);

                // Creating a new docuemnt with the Favorites model
                documentReference.set(new FavoritesDb(newlyAddedFavMovies))
                        .addOnSuccessListener(success -> {
                            toastMessage.setValue("Movie added to Favorites");
                        })
                        .addOnFailureListener(error -> {
                            toastMessage.setValue("Movie cannot be added");
                            Log.d("tag", "Cannot add movie" + error);
                        });
            }
        });

    }

}
