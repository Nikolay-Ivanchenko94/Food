package com.example.food.databse;
import com.example.food.foods.Foods;

import retrofit2.Call;
import retrofit2.http.GET;
public interface APIService {

    @GET("https://www.themealdb.com/api/json/v1/1/random.php")
    Call<Foods> getFood();

}
