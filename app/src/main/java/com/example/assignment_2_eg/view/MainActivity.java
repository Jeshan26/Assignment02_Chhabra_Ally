package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

//import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.adapters.MovieAdapter;
import com.example.assignment_2_eg.databinding.ActivityMainBinding;
import com.example.assignment_2_eg.interfaces.MovieClickListener;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.viewModel.MovieViewModel;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieClickListener {

    private ArrayList<MovieModel> movieList = new ArrayList<>(); // List to store movie data

    // instantiating required objects of ViewModel and View Binding
    MovieViewModel viewModel;
    ActivityMainBinding binding;

    MovieAdapter movieAdapter; // Adapter for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initializing view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        // Setting up RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.movieRecyclerView.setLayoutManager(layoutManager);

        // Initializing the adapter with an empty list
        movieAdapter = new MovieAdapter(getApplicationContext(),new ArrayList<>());
        binding.movieRecyclerView.setAdapter(movieAdapter); // Setting adapter to RecyclerView
        movieAdapter.setMovieClickListener(this);  // Setting click listener for movies

//        initiating viewModel and obsrving the change and setting to the vew and logging as well
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        viewModel.getMovieData().observe(this, movieData ->{
            Log.i("tag","View Updated"); // Logging when data updates
            movieList.clear(); // clear existing list
            movieList.addAll(movieData); // add the new data
            movieAdapter.updateMovies(movieData); // Update RecyclerView with new data

        });
// on click listener on search button that goes to viewModel SearchMovie function
        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = binding.movienameTV.getText().toString(); // Get movie name from input field
                viewModel.SearchMovie(movieName); // this will Trigger movie search in the ViewModel
            }
        });

        // Set the listener for item clicks
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();
            switch (itemID) {
                case R.id.searchID:
                    recreate();
                    break;

                case R.id.favoritesID:
                    // Start the FavoritesActivity
                    Intent favoritesIntent = new Intent(MainActivity.this, Favorities.class);
                    startActivity(favoritesIntent);
                    break;
            }
            return true; // Return true to indicate the item selection was handled
        });
    }

    // Handle movie item click to navigate to movie details
    @Override
    public void onClick(View view, int pos) {
        //  an Intent to open the MovieDetails activity
        Intent intentObj = new Intent(MainActivity.this, MovieDetails.class);

        // Passing the movie ID to the MovieDetails activity
        intentObj.putExtra("movie_id",movieList.get(pos).getID());


        startActivity(intentObj); // Start the MovieDetails activity


    }
}
