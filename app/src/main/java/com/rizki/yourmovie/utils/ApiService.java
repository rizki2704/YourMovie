package com.rizki.yourmovie.utils;

import com.rizki.yourmovie.data.source.remote.response.movie.MovieDetailsResponse;
import com.rizki.yourmovie.data.source.remote.response.movie.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MoviesResponse> getMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(
            @Path("id") String id,
            @Query("api_key") String apiKey
    );
}
