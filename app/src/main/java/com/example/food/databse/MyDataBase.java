package com.example.food.databse;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {FoodDB.class}, version = 1)
public abstract  class MyDataBase extends RoomDatabase {
    public abstract FoodDao foodDao();

}