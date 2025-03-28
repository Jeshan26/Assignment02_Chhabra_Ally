package com.example.assignment_2_eg.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Callback;

public class ApiClient {

    private static final OkHttpClient client = new OkHttpClient(); //OkHttpClient instance for network requests
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8"); //JSON request

    //get movie data from a given URL
    public static void getMovies(String url , Callback callback){
        Request request =  new Request.Builder() // Build the request with the provided URL
                .url(url) // Set the request URL
                .build(); // Build the request object

        client.newCall(request).enqueue(callback); // this Makes the request and handle the response in the callback
    }
}
