package com.example.assignment_2_eg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.interfaces.MovieClickListener;
import com.example.assignment_2_eg.model.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<MovieModel> movies; // List to hold movie data
    Context context; // Context to access resources

    MovieClickListener movieClickListener; // Listener to handle click events on movie items

    // Constructor to initialize context and movie data
    public MovieAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }
    // Set the click listener for movie items
    public void setMovieClickListener(MovieClickListener movieListener){
        this.movieClickListener = movieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each movie item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout,parent,false);
        // MovieViewHolder that passes the itemView and movieClickListener
        MovieViewHolder myViewHolder = new MovieViewHolder(itemView,this.movieClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel movie = movies.get(position); // Gets the movie data at the current position
      // Set the title and year for the movie item
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        // Load image from URL using Glide(library)
        Glide.with(context).load(movie.getPoster()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return movies.size(); // how many movies here . its array so array.size() ---- Returns the number of movies in the list
    }
    
    // Update the list of movies and refresh the RecyclerView
    public void updateMovies(List<MovieModel> movies) {
        this.movies = movies;  // Update the adapter's data --- basically sets the new movie data
        notifyDataSetChanged();   // Notify RecyclerView to refresh -- update the view
    }
}
