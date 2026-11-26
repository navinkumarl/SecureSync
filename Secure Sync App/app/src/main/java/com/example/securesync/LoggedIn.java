package com.example.securesync;

import static com.example.securesync.R.id.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.securesync.databinding.ActivityLoggedInBinding;

public class LoggedIn extends AppCompatActivity {
    TextView un_db;
    String un;
    private SharedPreferences.Editor ed;
    private ActivityLoggedInBinding activityLoggedInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoggedInBinding=ActivityLoggedInBinding
                .inflate(getLayoutInflater());
        setContentView(activityLoggedInBinding.getRoot());

        un_db=findViewById(username_dashboard);

        SharedPreferences sp = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        ed= sp.edit();
        un= sp.getString("username","");

        un_db.setText(un);
        activityLoggedInBinding.usernameLogin.setText(un);
    }

    public void ClickMenu(View view) {
        activityLoggedInBinding.drawerLayer.openDrawer(GravityCompat.START);
    }

    public void Sign_Out(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            ed.putBoolean("isLoggedIn",false);
            ed.apply();
            Intent myint=new Intent(LoggedIn.this, MainActivity.class);
            startActivity(myint);
        });
        builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }

    public void Movies_List(View view) {
        Intent myint=new Intent(LoggedIn.this, Movies.class);
        startActivity(myint);
    }

    public void Home_Screen(View view) {
        activityLoggedInBinding.drawerLayer.closeDrawer(GravityCompat.START);
    }

    public void Users_List(View view) {
        Intent myint=new Intent(LoggedIn.this, Users.class);
        startActivity(myint);
    }

    @Override
    public void onBackPressed() {
        if(activityLoggedInBinding.drawerLayer.isDrawerOpen(GravityCompat.START)) {
            activityLoggedInBinding.drawerLayer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to exit the app?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> finishAffinity());
            builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
            builder.setCancelable(false);
            builder.show();
        }
    }
}