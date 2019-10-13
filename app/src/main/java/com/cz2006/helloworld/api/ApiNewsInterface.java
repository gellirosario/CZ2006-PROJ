package com.cz2006.helloworld.api;

import com.cz2006.helloworld.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiNewsInterface {

    @GET("everything")
    Call<News> getNews(

            @Query("q") String search,
            @Query("apiKey") String apiKey

    );
}
