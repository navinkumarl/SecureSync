package com.example.securesync;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

public class initialiseDatabase extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
