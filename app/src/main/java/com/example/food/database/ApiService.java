package com.example.food.database;

import com.example.food.yep.Yep;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("https://api.yelp.com/v3/businesses/search?location=NYC")
    Call<Yep> getYep();
}
