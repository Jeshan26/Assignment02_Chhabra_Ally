package com.example.assignment_2_eg.adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.interfaces.MovieClickListener;
import com.example.assignment_2_eg.model.MovieModel;

import java.util.ArrayList;

public class MovieViewHolder extends RecyclerView.ViewHolder {


    ImageView imageView;
    TextView title;
    TextView year;

    MovieClickListener movieClickListener;

    public MovieViewHolder(@NonNull View itemView, MovieClickListener movieClickListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imagePosterView);
        title = itemView.findViewById(R.id.movie_title);
        year = itemView.findViewById(R.id.movie_year);

        this.movieClickListener = movieClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onViewHolder Click");
                movieClickListener.onClick(v,getAdapterPosition());
            }
        });
    }
}
