package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.adapters.MovieAdapter;
import com.example.assignment_2_eg.databinding.ActivityFavoritiesBinding;
import com.example.assignment_2_eg.interfaces.MovieClickListener;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.viewModel.FavoritesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Favorities extends AppCompatActivity implements MovieClickListener {

    ActivityFavoritiesBinding binding;

    private ArrayList<MovieModel> favmovieList = new ArrayList<>(); // List to store movie data

    private List<String> movieList = new ArrayList<>();

    //    Cloud firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get a reference to the 'FavMovies' collection
    CollectionReference collectionReference = db.collection("FavMovies");
    //   Getting the userid of the logged in user to add the data for user specific
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference documentReference = collectionReference.document(userId);

    FavoritesViewModel favoritesViewModel; // ViewModel to manage movie details data

    MovieAdapter movieAdapter; // Adapter for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tagDubara", "onCreate: ");

//        setContentView(R.layout.activity_favorities);
        //        initializing view binding
        binding = ActivityFavoritiesBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        // Setting up RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.favoritesRecyclerView.setLayoutManager(layoutManager);

        // Initializing the adapter with an empty list
        movieAdapter = new MovieAdapter(getApplicationContext(),new ArrayList<>());
        binding.favoritesRecyclerView.setAdapter(movieAdapter); // Setting adapter to RecyclerView
        movieAdapter.setMovieClickListener(this);  // Setting click listener for movies

        // Initializing the ViewModel to get movie details
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        favoritesViewModel.getFavoritesMovieDetailsData().observe(this, favMovieData ->{
            Log.i("tag","View Updated"); // Logging when data updates
            favmovieList.clear(); // clear existing list
            favmovieList.addAll(favMovieData); // add the new data
            movieAdapter.updateMovies(favMovieData); // Update RecyclerView with new data

        });


        favoritesViewModel.fetchFavoritesMovieDetails();
//        documentReference.get().addOnSuccessListener(documentSnapshot -> {
//            List<String> favMoviesList = (List<String>) documentSnapshot.get("favMoviesList");
//            favoritesViewModel.fetchFavoritesMovieDetails(favMoviesList);
//        });



//        making sure when the fav menu is clicked its highlighted
        binding.bottomNavigation.setSelectedItemId(R.id.favoritesID);

        // Set the listener for item clicks
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            switch (itemID) {
                case R.id.searchID:
                    // Start the FavoritesActivity
                    Intent favoritesIntent = new Intent(this, MainActivity.class);
                    startActivity(favoritesIntent);
                    break;

                case R.id.favoritesID:
                    recreate();
                    break;
            }
            return true; // Return true to indicate the item selection was handled
        });

    }


    @Override
    public void onClick(View view, int pos) {

        //  an Intent to open the favorites details activity
        Intent intentObj = new Intent(Favorities.this, FavoritesDetails.class);

        // Passing the movie ID to the MovieDetails activity
        intentObj.putExtra("movie_title",favmovieList.get(pos).getTitle());


        startActivity(intentObj); // Start the MovieDetails activity

    }
// will use this to update once a movie is deleted
//    @Override
//    protected void onResume() {
//        super.onResume();
//        for (String list : movieList){
//            System.out.println(list);
//            Log.d("tag", "onResume: " + list);
//        }
//
//        favoritesViewHolder.fetchFavoritesMovieDetails(movieList); // Call a method that refreshes LiveData from Firestore
//    }
}