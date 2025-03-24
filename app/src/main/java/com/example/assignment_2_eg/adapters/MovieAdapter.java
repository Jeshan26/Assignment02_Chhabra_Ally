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

    List<MovieModel> movies;
    Context context;

    MovieClickListener movieClickListener;

    public MovieAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setMovieClickListener(MovieClickListener movieListener){
        this.movieClickListener = movieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout,parent,false);
        MovieViewHolder myViewHolder = new MovieViewHolder(itemView,this.movieClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel movie = movies.get(position);

        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
//
        // Load image from URL using Glide(library)
        Glide.with(context).load(movie.getPoster()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return movies.size(); // how many movies here . its array so array.size()
    }

    public void updateMovies(List<MovieModel> movies) {
        this.movies = movies;  // Update the adapter's data
        notifyDataSetChanged();   // Notify RecyclerView to refresh
    }
}
