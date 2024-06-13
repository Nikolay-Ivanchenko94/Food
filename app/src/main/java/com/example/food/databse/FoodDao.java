package com.example.food.databse;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    public void insertFoodDBList(List<FoodDB> foodDBS);

    @Query("SELECT * FROM FoodDB")
    public List<FoodDB> getFoodDBS();
}


