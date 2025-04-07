package com.example.assignment_2_eg.viewModel;

import android.graphics.Movie;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment_2_eg.model.FavoritesDb;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

public class FavoritesDetailsViewModel extends ViewModel {

    //    Cloud firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get a reference to the 'FavMovies' collection
    CollectionReference collectionReference = db.collection("FavMovies");
    //   Getting the userid of the logged in user to add the data for user specific
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = collectionReference.document(userId);

    private final MutableLiveData<MovieModel> favMovieData = new MutableLiveData<>();

    public LiveData<MovieModel> getFavMovieDetailsData() {
        return favMovieData;
    }

    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void getMovieDetails(String movieTitle) {
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
//            firebase stores as key value pairs(map) so we have to get the data and add to movieModel list for comparing
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
            for (MovieModel m : favMoviesList) {
                if (m.getTitle().equalsIgnoreCase(movieTitle)) {
                    favMovieData.postValue(m);
                }
            }
        });
    }

    //
    public void deleteFavMovie(String movieTitle) {
//        using  lamdas(arrow function instead of traditional class calling for better readability)
        documentReference.get().addOnSuccessListener(documentSnapshot -> {


            if (documentSnapshot.exists()) {
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
//                try catch to find runtime error on delete
                try {
                    for (MovieModel m : favMoviesList) {
                        if (m.getTitle().equalsIgnoreCase(movieTitle)) {
                            favMoviesList.remove(m); // removing the movie object
                            documentReference.update("favMoviesList", favMoviesList)
                                    .addOnSuccessListener(success -> {
                                        // Fetching context from the click
                                        toastMessage.setValue("Movie removed from favorites!");
                                    })
                                    .addOnFailureListener(error -> {
                                        toastMessage.setValue("Cannot remove the movie");
                                        Log.d("tag", "Cannot remove movie" + error);
                                    });
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.d("tag", "Exception" + e);
                }
            } else {
                // Document doesn't exist, handle as needed
                toastMessage.setValue("Document not found!");
            }
        });
    }

    public void updateFavMovieDescription(String movieTitle,String description) {
        documentReference.get().addOnSuccessListener(documentSnapshot -> {

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

            //                try catch to find runtime error on delete
            try {
                for (MovieModel m : favMoviesList) {
                    if (m.getTitle().equalsIgnoreCase(movieTitle)) {
                        m.setStudio(description);
                        documentReference.update("favMoviesList", favMoviesList)
                                .addOnSuccessListener(success -> {
                                    // Fetching context from the click
                                    toastMessage.setValue("Movie description has been updated");
                                })
                                .addOnFailureListener(error -> {
                                    toastMessage.setValue("Cannot update the description");
                                    Log.d("tag", "Cannot update the description" + error);
                                });
                        break;
                    }
                }
            } catch (Exception e) {
                Log.d("tag", "Exception" + e);
            }


        });

    }
}