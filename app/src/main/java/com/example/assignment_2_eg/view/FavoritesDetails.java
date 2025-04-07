package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.databinding.ActivityFavoritesDetailsBinding;
import com.example.assignment_2_eg.databinding.ActivityMovieDetailsBinding;
import com.example.assignment_2_eg.viewModel.FavoritesDetailsViewModel;
import com.example.assignment_2_eg.viewModel.MovieDetailsViewModel;

public class FavoritesDetails extends AppCompatActivity {

    ActivityFavoritesDetailsBinding binding;

    FavoritesDetailsViewModel favoritesDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_favorites_details);
        // Initializing view binding
        binding = ActivityFavoritesDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        favoritesDetailsViewModel = new ViewModelProvider(this).get(FavoritesDetailsViewModel.class);

        favoritesDetailsViewModel.getFavMovieDetailsData().observe(this,favMovieData ->{
            // Setting movie details to the their TextViews
            binding.movietitleTV.setText(favMovieData.getTitle());
            binding.genreTV.setText(favMovieData.getGenre());
            binding.movieplot.setText(favMovieData.getStudio());
            binding.runtimeTV.setText(favMovieData.getRuntime());
            binding.movieyearTV.setText(favMovieData.getYear());
            binding.movierateTV.setText(favMovieData.getRated());
            binding.movielanguageTV.setText(favMovieData.getLanguage());
            // Loading movie poster image using Glide
            Glide.with(this).load(favMovieData.getPoster()).into(binding.movieposter);
        });

        //        observer for the toast msg display
        favoritesDetailsViewModel.getToastMessage().observe(this,toastMessage -> {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        });


        Intent intentObj = getIntent();
        String movie_title = intentObj.getStringExtra("movie_title");

        // If a movie title was received, it'll fetch its details from the ViewModel
        if (movie_title != null) {
            favoritesDetailsViewModel.getMovieDetails(movie_title);
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesDetailsViewModel.deleteFavMovie(movie_title);
                finish();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                passing the updated description so it gets stored
                favoritesDetailsViewModel.updateFavMovieDescription(binding.movietitleTV.getText().toString(),binding.movieplot.getText().toString());
                finish();
            }
        });
    }




}