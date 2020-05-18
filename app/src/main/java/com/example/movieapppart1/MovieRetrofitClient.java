package com.example.movieapppart1;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRetrofitClient {

    private static Service service;
    public static Service getRetrofitClient(Context context){
        if (service == null){

            Gson gson = new GsonBuilder().create();
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service =retrofit.create(Service.class);
        }

        return service;
    }
}
