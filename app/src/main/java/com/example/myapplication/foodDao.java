package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface foodDao {


    @Insert
    void insert(food ...foods);

    @Query("select totalcounted from food where name = :item_name")
    int get_totalcounted(String item_name);

    @Query("select totalratings from food where name = :item_name")
    int get_totalratings(String item_name);

    @Query("UPDATE food set totalcounted = :n where name = :item_name")
    void update_totalcounted(int n , String item_name);

    @Query("UPDATE food set totalratings = :n where name = :item_name")
    void update_totalratings(int n , String item_name);

    @Query("select * from food where name = :food_name")
    List<food> checkpresent(String food_name);

}
