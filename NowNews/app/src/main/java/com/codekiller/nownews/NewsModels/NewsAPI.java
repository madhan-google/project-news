package com.codekiller.nownews.NewsModels;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("top-headlines")
    Call<TopHeadlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String api_key
    );

    @GET("everything")
    Call<Everything> getEverything(
            @Query("q") String q,
            @Query("apiKey") String api_key
    );
}
