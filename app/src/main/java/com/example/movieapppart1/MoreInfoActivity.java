package com.example.movieapppart1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class MoreInfoActivity extends AppCompatActivity {
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView userRating;
    private TextView releaseDate;
    private TextView movieSynopsis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);

        //ActionBar and its title
        ActionBar actionBar = getSupportActionBar();



        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        moviePoster = findViewById(R.id.movie_thumbnail);
        movieTitle = findViewById(R.id.movie_title);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.release_date);
        movieSynopsis = findViewById(R.id.movie_synopsis);

        Bundle bundle = getIntent().getExtras();
        MyMovie mymovie = (MyMovie) bundle.getSerializable("movieData");
        getMovieData(mymovie);

    }
    private void getMovieData(MyMovie mymovie) {
        Glide.with(this)
                .load(imagePath(mymovie.getPosterPath()))
                .into(moviePoster);

        movieTitle.setText(mymovie.getTitle());
        userRating.setText(String.format("%s/10", mymovie.getVoteAverage()));
        releaseDate.setText(mymovie.getReleaseDate());
        movieSynopsis.setText(mymovie.getOverview());
    }

    private static String imagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/" + "w500" + posterPath;
    }
}
