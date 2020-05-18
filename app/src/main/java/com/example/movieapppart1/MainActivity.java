package com.example.movieapppart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    //Set standard sort mode to Popular
    private static int SORT_MODE = 1;
    private static int PAGE = 1;

    private RecyclerView recyclerView;
    private Adapter adapter;



    private List<MyMovie> moviesData;
    private Menu menu;

    private Call<Result> movieResultCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rv_movie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        boolean flag = checkInternetConnectivity();
        if (flag){
            showRecyclerView();
        }else {
            showNoConnection();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setIcon(R.drawable.ic_sort);
        switch (item.getItemId()) {
            case R.id.action_popular:
                SORT_MODE = 1;
                setTitle(this.getString(R.string.app_name));
                break;
            case R.id.action_most_rated:
                SORT_MODE = 2;
                setTitle(this.getString(R.string.top_rated));
                break;
        }
        loadPage(PAGE);
        return super.onOptionsItemSelected(item);
    }


    private void loadPage(final int page) {
        Service service = MovieRetrofitClient.getRetrofitClient(MainActivity.this);
        switch (SORT_MODE) {
            case 1:
                movieResultCall = service.getPopularMovies(page, MainActivity.this.getString(R.string.apiKey));
                break;

            case 2:
                movieResultCall = service.getTopRatedMovies(page, MainActivity.this.getString(R.string.apiKey));
                break;
        }

        movieResultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()){
                    if (page ==1){
                        if (response.body() != null) {
                            generateData(response.body().getMyMovies());
                        }
                    }else{
                        List<MyMovie> myMovieList = null;
                        if (response.body() != null) {
                            myMovieList = response.body().getMyMovies();
                        }
                        if (myMovieList != null) {
                            for (MyMovie mymovie : myMovieList){
                                moviesData.add(mymovie);
                                adapter.notifyItemChanged(moviesData.size()-1);

                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void generateData(List<MyMovie> myMovies){
        adapter = new Adapter(myMovies, new OnItemClickListener() {
            @Override
            public void onItemClick(MyMovie mymovie) {
                Log.d("Movie Clicked", mymovie.getTitle());

                Intent intent = new Intent(getApplicationContext(), MoreInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movieData", mymovie);
                intent.putExtras(bundle);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public void showNoConnection(){

    }

    public void showRecyclerView(){
        loadPage(PAGE);

    }

    private boolean checkInternetConnectivity() {
        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();

        }
        return networkInfo != null && networkInfo.isConnected();

    }

}
