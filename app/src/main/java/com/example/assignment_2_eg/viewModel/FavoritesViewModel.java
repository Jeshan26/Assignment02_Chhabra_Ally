package com.example.assignment_2_eg.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FavoritesViewModel extends ViewModel {
    MovieModel movie;

    //    Cloud firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get a reference to the 'FavMovies' collection
    CollectionReference collectionReference = db.collection("FavMovies");
    //   Getting the userid of the logged in user to add the data for user specific
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = collectionReference.document(userId);

    private final MutableLiveData<List<MovieModel>> favoritesMovieData = new MutableLiveData<>();

    public LiveData<List<MovieModel>> getFavoritesMovieDetailsData(){
        return favoritesMovieData;
    }

    public void fetchFavoritesMovieDetails(){

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
//            for (MovieModel movie : favMoviesList){
//                Log.d("tagmovie", "fetchFavoritesMovieDetails: " + movie.getTitle());
//                Log.d("tagmovie", "fetchFavoritesMovieDetails: " + movie.getStudio());
//            }
//            posting the arraylist to the live data which is getting observed in favorites activity
            favoritesMovieData.postValue(favMoviesList);
        });
    }
}
