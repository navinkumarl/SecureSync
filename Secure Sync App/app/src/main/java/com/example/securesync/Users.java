package com.example.securesync;

import static com.example.securesync.R.id.username_dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securesync.databinding.ActivityUsersBinding;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {
    TextView un_db;
    String un;
    private SharedPreferences.Editor ed;
    public static ArrayList<ExampleItem> data;
    private ActivityUsersBinding activityUsersBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUsersBinding=ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(activityUsersBinding.getRoot());
        un_db=findViewById(username_dashboard);

        SharedPreferences sp=getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        ed= sp.edit();
        un= sp.getString("username","");

        un_db.setText(un);

        data=new ArrayList<>();
        DatabaseClass databaseClass=DatabaseClass.getInstance(this);
        //noinspection deprecation
        new GetUserAsyncTask(databaseClass).execute();

        activityUsersBinding.recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter<DataAdapter.DataViewHolder> adapter = new DataAdapter(data);
        activityUsersBinding.recyclerView.setLayoutManager(layoutManager);
        activityUsersBinding.recyclerView.setAdapter(adapter);
    }

    public void ClickMenu(View view) {
        activityUsersBinding.usersDrawer.openDrawer(GravityCompat.START);
    }

    public void Sign_Out(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            ed.putBoolean("isLoggedIn",false);
            ed.apply();
            Intent myint=new Intent(Users.this, MainActivity.class);
            startActivity(myint);
        });
        builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }

    public void Movies_List(View view) {
        Intent myint=new Intent(Users.this, Movies.class);
        startActivity(myint);
    }

    public void Users_List(View view) {
        activityUsersBinding.usersDrawer.closeDrawer(GravityCompat.START);
    }

    public void Home_Screen(View view) {
        Intent myint=new Intent(Users.this, LoggedIn.class);
        startActivity(myint);
    }

    @Override
    public void onBackPressed() {
        if(activityUsersBinding.usersDrawer.isDrawerOpen(GravityCompat.START)) {
            activityUsersBinding.usersDrawer.closeDrawer(GravityCompat.START);
        } else {
            Intent myint = new Intent(Users.this, LoggedIn.class);
            startActivity(myint);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetUserAsyncTask extends AsyncTask<Void, Void, UserModel> {
        private final DatabaseClass databaseClass;

        /** @noinspection deprecation*/
        public GetUserAsyncTask(DatabaseClass databaseClass) {
            this.databaseClass = databaseClass;
        }

        /** @noinspection deprecation*/
        @Override
        protected UserModel doInBackground(Void... voids) {
            List<UserModel> userModels=databaseClass.daoClass().viewUser();
            for(UserModel user:userModels) {
                data.add(new ExampleItem(user.getName(),user.getPhone(),user.getMail()));
            }
            return null;
        }

        //@Override
        protected void onPostExecute(List<UserModel> userModels) {
            if (userModels==null) {
                Toast.makeText(getApplicationContext(),"No Registered Users!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}