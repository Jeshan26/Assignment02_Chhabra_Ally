package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.assignment_2_eg.databinding.ActivityMainBinding;
import com.example.assignment_2_eg.databinding.ActivityMovieDetailsBinding;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.utils.ApiClient;
import com.example.assignment_2_eg.viewModel.MovieDetailsViewModel;
import com.example.assignment_2_eg.viewModel.MovieViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetails extends AppCompatActivity {

    ActivityMovieDetailsBinding binding; // View binding to access UI elements easily
    MovieDetailsViewModel movieDetailsViewModel; // ViewModel to manage movie details data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing view binding
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_movie_details);
        
        // Initializing the ViewModel to get movie details
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        // Observing LiveData from ViewModel to update UI when movie details change
        movieDetailsViewModel.getMovieDetailsData().observe(this,movieData ->{
            
            // Setting movie details to the their TextViews
            binding.movietitleTV.setText(movieData.getTitle());
            binding.movieratingsTV.setText(movieData.getRating());
            binding.genreTV.setText(movieData.getGenre());
            binding.movieplot.setText(movieData.getStudio());
            binding.runtimeTV.setText(movieData.getRuntime());
            binding.movieyearTV.setText(movieData.getYear());
            binding.movierateTV.setText(movieData.getRated());
            binding.movielanguageTV.setText(movieData.getLanguage());
            // Loading movie poster image using Glide
            Glide.with(this).load(movieData.getPoster()).into(binding.movieposter);

        });

        // Getting movie ID from the previous screen (passed through Intent)
        Intent intentObj = getIntent(); 
        String movie_id = intentObj.getStringExtra("movie_id");

        // If a movie ID was received, it'll fetch its details from the ViewModel
        if (movie_id != null) {
            movieDetailsViewModel.getMovieDetails(movie_id);
        }

        // Setting click listener for the back button to close the activity
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Closes the current activity and returns to the previous screen
                finish();
            }
        });

    }
}
