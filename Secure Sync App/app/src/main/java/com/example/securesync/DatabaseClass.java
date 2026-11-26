package com.example.securesync;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserModel.class},version = 1,exportSchema = false)
public abstract class DatabaseClass extends RoomDatabase {
    private static DatabaseClass instance;
    public abstract DaoClass daoClass();
    public static synchronized DatabaseClass getInstance(Context context) {
        if(instance==null) {
            instance= Room.databaseBuilder(context.getApplicationContext(),DatabaseClass.class,"Users_Info").fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallBack=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
