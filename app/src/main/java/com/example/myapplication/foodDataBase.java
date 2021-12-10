package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {food.class}, version  = 1)
public abstract class foodDataBase extends RoomDatabase {

    public abstract foodDao itemDao();

    private static foodDataBase INSTANCE;

    public static foodDataBase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), foodDataBase.class, "ITEM_DB").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}

