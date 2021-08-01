package com.rizki.yourmovie.data.source.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rizki.yourmovie.data.source.remote.response.movie.MovieDetailsResponse;
import com.rizki.yourmovie.data.source.remote.response.movie.MoviesItem;
import com.rizki.yourmovie.data.source.remote.response.movie.MoviesResponse;
import com.rizki.yourmovie.utils.ApiConfig;
import com.rizki.yourmovie.utils.EspressoIdlingResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rizki.yourmovie.utils.ApiData.API_KEY;

public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<MoviesItem>>> getAllMovies()
    {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MoviesItem>>> result = new MutableLiveData<>();
        Call<MoviesResponse> client = ApiConfig.getApiService().getMovies(API_KEY);
        client.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                result.setValue(ApiResponse.success(response.body().getResults()));
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("onFailure", "getAllMovies: "+ t.getMessage());

            }
        });
        return result;
    }
    public LiveData<ApiResponse<MovieDetailsResponse>> getMovieDetails(String movieId){
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<MovieDetailsResponse>> result = new MutableLiveData<>();
        Call<MovieDetailsResponse> client = ApiConfig.getApiService().getMovieDetails(movieId, API_KEY);
        client.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                result.setValue(ApiResponse.success(response.body()));
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                Log.e("onFailure", "getMovieDetails: "+ t.getMessage());
            }
        });
        return result;
    }
}
