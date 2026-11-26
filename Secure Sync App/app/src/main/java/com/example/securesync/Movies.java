package com.example.securesync;

import static com.example.securesync.R.id.username_dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securesync.databinding.ActivityMoviesBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


/** @noinspection ALL*/
public class Movies extends AppCompatActivity {

    interface RequestMovies {
        @GET("v1/getList")
        Observable<Post> getMoviesData();
    }

    TextView un_db;
    String un;

    private MovieAdapter adapter;
    private SharedPreferences.Editor ed;
    private ActivityMoviesBinding activityMoviesBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMoviesBinding = ActivityMoviesBinding
                .inflate(getLayoutInflater());
        setContentView(activityMoviesBinding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        un_db = findViewById(username_dashboard);

        SharedPreferences sp = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        ed = sp.edit();
        un = sp.getString("username", " ");

        un_db.setText(un);

        activityMoviesBinding.movieRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        activityMoviesBinding.movieRecyclerView.setLayoutManager(layoutManager);

        load_data();
    }

    public void load_data() {
        progressDialog.show();
        api_call();
    }

    public void api_call() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://demo3540805.mockable.io/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestMovies requestMovies=retrofit.create(RequestMovies.class);
        requestMovies.getMoviesData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Post>() {
                    @Override
                    public void onNext(@NonNull Post post) {
                        activityMoviesBinding.moviesTitle.setText(post.getData().getTitle());
                        if (post.isResult()) {
                            ArrayList<MoviesData> moviesDataList = (ArrayList<MoviesData>) post.getData().getMovies();
                            adapter = new MovieAdapter(moviesDataList);
                            //adapter.notifyDataSetChanged();
                            activityMoviesBinding.movieRecyclerView.setAdapter(adapter);
                            adapter.setMovieList(moviesDataList);
                        } else {
                            Toast.makeText(Movies.this, R.string.api_request_unsuccessful
                                    , Toast.LENGTH_SHORT).show();
                            activityMoviesBinding.moviesTitle.setText(R.string.api_request_unsuccessful);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(Movies.this, R.string.api_request_failed
                                , Toast.LENGTH_SHORT).show();
                        activityMoviesBinding.moviesTitle.setText(R.string.api_request_failed);
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
//        RequestMovies requestMovies=retrofit.create(RequestMovies.class);
//        requestMovies.getMovies().enqueue(new Callback<Post>() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
//                if(response.isSuccessful() && response.body()!=null) {
//                    activityMoviesBinding.moviesTitle.setText(response.body().getData().getTitle());
//                    if(response.body().isResult()) {
//                        ArrayList<MoviesData> moviesDataList=(ArrayList<MoviesData>) response.body().getData().getMovies();
//                        adapter=new MovieAdapter(moviesDataList);
//                        adapter.notifyDataSetChanged();
//                        activityMoviesBinding.movieRecyclerView.setAdapter(adapter);
//                        adapter.setMovieList(moviesDataList);
//                    } else {
//                        Toast.makeText(Movies.this,"API Request Unsuccessful!"
//                                ,Toast.LENGTH_SHORT).show();
//                        activityMoviesBinding.moviesTitle.setText(response.code());
//                    }
//                } else {
//                    Toast.makeText(Movies.this,"API Response Failed!"
//                            ,Toast.LENGTH_SHORT).show();
//                    activityMoviesBinding.moviesTitle.setText(response.code());
//                }
//            }
//            @Override
//            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
//                activityMoviesBinding.moviesTitle.setText(t.getMessage());
//                Toast.makeText(Movies.this,"API Request Failed!"
//                        ,Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (activityMoviesBinding.moviesDrawer.isDrawerOpen(GravityCompat.START)) {
            activityMoviesBinding.moviesDrawer.closeDrawer(GravityCompat.START);
        } else {
            Intent myint = new Intent(Movies.this, LoggedIn.class);
            startActivity(myint);
        }
    }

    public void ClickMenu(View view) {
        activityMoviesBinding.moviesDrawer.openDrawer(GravityCompat.START);
    }

    public void Sign_Out(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            ed.putBoolean("isLoggedIn", false);
            ed.apply();
            Intent myint = new Intent(Movies.this, MainActivity.class);
            startActivity(myint);
        });
        builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }

    public void Movies_List(View view) {
        activityMoviesBinding.moviesDrawer.closeDrawer(GravityCompat.START);
    }

    public void Home_Screen(View view) {
        Intent myint = new Intent(Movies.this, LoggedIn.class);
        startActivity(myint);
    }

    public void Users_List(View view) {
        Intent myint = new Intent(Movies.this, Users.class);
        startActivity(myint);
    }
}