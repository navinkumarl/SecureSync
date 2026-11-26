package com.example.securesync;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.securesync.databinding.ActivityLoggedInBinding;
import com.example.securesync.databinding.ActivityMainBinding;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {
    private DatabaseClass databaseClass;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding
                .inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        databaseClass=DatabaseClass.getInstance(this);

        activityMainBinding.loginButton.setOnClickListener(view -> {
            if(activityMainBinding.username.getText().toString().isEmpty()
                    || activityMainBinding.password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"Enter Username and/or Password!",
                        Toast.LENGTH_SHORT).show();
            } else {
                //noinspection deprecation
                new GetUserAsyncTask(databaseClass,activityMainBinding.username.getText().toString()
                        ,activityMainBinding.password.getText().toString()).execute();
            }
        });
        activityMainBinding.signUp.setOnClickListener(view -> {
            Intent myint=new Intent(this,SignUp.class);
            startActivity(myint);
        });
    }

    public void showAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sign In");
        builder.setMessage("Sign In Successful");
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            Intent myint=new Intent(this,LoggedIn.class);
            startActivity(myint);
                });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> finishAffinity());
        builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetUserAsyncTask extends AsyncTask<Void, Void, UserModel> {
        private final DatabaseClass databaseClass;
        private final String username;
        private final String password;

        public GetUserAsyncTask(DatabaseClass databaseClass,String username,String password) {
            this.databaseClass = databaseClass;
            this.username = username;
            this.password = password;
        }

        @Override
        protected UserModel doInBackground(Void... voids) {
            return databaseClass.daoClass().loginUser(username, password);
        }

        @Override
        protected void onPostExecute(UserModel user) {
            if (user!=null) {
                SharedPreferences sp=getSharedPreferences("Preferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("isLoggedIn", true);
                ed.putString("username", activityMainBinding.username.getText().toString());
                ed.apply();
                showAlert();
            } else {
                Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}