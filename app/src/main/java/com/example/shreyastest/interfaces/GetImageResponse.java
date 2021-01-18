package com.example.shreyastest.Interfaces;

import com.example.shreyastest.BeanClasses.ApiSearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetImageResponse {

    @GET("gallery/search/1")
    Single<ApiSearchResponse> getApiResponse(@Query("q") String word);

}
