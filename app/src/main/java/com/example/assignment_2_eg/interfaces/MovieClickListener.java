package com.example.assignment_2_eg.interfaces;

import android.view.View;

public interface MovieClickListener {
     //called when movie item is clicked
    // 'view' represents the view that was clicked, and 'pos' is the position of the clicked item in the list

    void onClick(View view, int pos);
}
