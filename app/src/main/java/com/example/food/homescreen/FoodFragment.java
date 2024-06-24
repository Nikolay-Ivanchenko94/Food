package com.example.food.homescreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.food.database.ApiService;
import com.example.food.database.FoodDB;
import com.example.food.database.MyDataBase;
import com.example.food.databinding.FragmentFoodBinding;
import com.example.food.yep.Yep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FoodFragment extends Fragment {
    private FragmentFoodBinding binding;
    private ApiService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFoodBinding.inflate(inflater, container, false);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
        requestFood();

        return binding.getRoot();
    }

    private void requestFood() {
        service.getYep().enqueue(new retrofit2.Callback<Yep>() {
            @Override
            public void onResponse(retrofit2.Call<Yep> call, retrofit2.Response<Yep> response) {
                if (response.isSuccessful() && response.body() != null){

                    MyDataBase myDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                            MyDataBase.class, "database-name").build();


                    ArrayList<FoodDB> weatherDBSList = new ArrayList<>();
                    Yep yep = response.body();
                    FoodDB foodDB = new FoodDB();
                    foodDB.setName(yep.getBusinesses().getname());
                    foodDB.setPrice(yep.getTransaction().getPrice());
                    weatherDBSList.add(foodDB);

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {

                        myDataBase.foodDao().getFoodDBList();
                        List<FoodDB> foodDBS = myDataBase.foodDao().getFoodDBList();
                        getActivity().runOnUiThread(() -> {
                            setDataToViews(foodDB);
                        });
                    });

                }
            }
            @Override
            public void onFailure(retrofit2.Call<Yep> call, Throwable t) {
                Log.d("ERROR", "error" + t.toString());
            }
        });

    }

    private void setDataToViews(FoodDB foodDB) {
       binding.tvGramercy.setText(String.valueOf(foodDB.getName()));
       binding.tvKie.setText(String.valueOf(foodDB.getName()));
       binding.tvPrice.setText(String.valueOf(foodDB.getPrice()));

    }
}