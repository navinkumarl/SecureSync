package com.example.securesync;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sp=getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        boolean flag=sp.getBoolean("isLoggedIn",false);

        new Handler().postDelayed(() -> {
            Intent myint;
            if(flag) {
                myint = new Intent(SplashScreen.this, LoggedIn.class);
            } else {
                myint = new Intent(SplashScreen.this, MainActivity.class);
            }
            startActivity(myint);
            finish();
        },1500);
    }
}