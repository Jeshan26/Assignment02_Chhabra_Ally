package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.adapters.MovieAdapter;
import com.example.assignment_2_eg.databinding.ActivityMainBinding;
import com.example.assignment_2_eg.interfaces.MovieClickListener;
import com.example.assignment_2_eg.model.MovieModel;
import com.example.assignment_2_eg.viewModel.MovieViewModel;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieClickListener {

    private ArrayList<MovieModel> movieList = new ArrayList<>();

//    instantiating required objects
    MovieViewModel viewModel;
    ActivityMainBinding binding;

    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.movieRecyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(getApplicationContext(),new ArrayList<>());
        binding.movieRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setMovieClickListener(this);


//        initiating viewModel and obsrving the change and setting to the vew and logging as well
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);


        viewModel.getMovieData().observe(this, movieData ->{
            Log.i("tag","View Updated");
            movieList.clear(); // clear existing list
            movieList.addAll(movieData); // add the new data
            movieAdapter.updateMovies(movieData);

        });
// on click listener on search button that goes to viewModel SearchMovie function
        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = binding.movienameTV.getText().toString();
                viewModel.SearchMovie(movieName);
            }
        });
    }

    @Override
    public void onClick(View view, int pos) {

        Intent intentObj = new Intent(MainActivity.this, MovieDetails.class);


        intentObj.putExtra("movie_id",movieList.get(pos).getID());


        startActivity(intentObj);


    }
}