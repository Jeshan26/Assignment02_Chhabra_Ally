package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.assignment_2_eg.R;
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

    MovieModel movie;

    ActivityMovieDetailsBinding binding;
    MovieDetailsViewModel movieDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_movie_details);

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        movieDetailsViewModel.getMovieDetailsData().observe(this,movieData ->{

            binding.movietitleTV.setText(movieData.getTitle());
            binding.movieratingsTV.setText(movieData.getRating());
            binding.genreTV.setText(movieData.getGenre());
            binding.movieplot.setText(movieData.getStudio());
            binding.runtimeTV.setText(movieData.getRuntime());
            binding.movieyearTV.setText(movieData.getYear());
            binding.movierateTV.setText(movieData.getRated());
            binding.movielanguageTV.setText(movieData.getLanguage());
            Glide.with(this).load(movieData.getPoster()).into(binding.movieposter);

        });

        Intent intentObj = getIntent();

        String movie_id = intentObj.getStringExtra("movie_id");

        if (movie_id != null) {
            movieDetailsViewModel.getMovieDetails(movie_id);
        }



        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}