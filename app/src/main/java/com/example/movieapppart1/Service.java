package com.example.movieapppart1;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    //Top Rated Movie
    @GET("movie/top_rated")
    Call<Result> getTopRatedMovies(@Query("page") int page, @Query("api_key") String apiKey);


    //Most popular movies
    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);

}
