package com.example.food.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    public void insertFoodDBList(List<FoodDB> foodDB);

    @Query("SELECT * FROM FoodDB")
    public List<FoodDB> getFoodDBList();

}
