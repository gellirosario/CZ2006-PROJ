package com.cz2006.helloworld.api;

import com.cz2006.helloworld.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Represents ApiNewsInterface
 *
 * @author Edmund
 */
public interface ApiNewsInterface {

    @GET("everything")
    Call<News> getNews(

            @Query("q") String search,
            @Query("domains") String domain,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
}
