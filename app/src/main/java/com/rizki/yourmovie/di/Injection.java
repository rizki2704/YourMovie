package com.rizki.yourmovie.di;

import android.content.Context;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.data.source.local.LocalDataSource;
import com.rizki.yourmovie.data.source.local.room.MoviesDatabase;
import com.rizki.yourmovie.data.source.remote.RemoteDataSource;
import com.rizki.yourmovie.utils.AppExecutors;

public class Injection {
    public static MovieRepository provideRepository(Context context){
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.moviesDao());
        AppExecutors appExecutors = new AppExecutors();
        return MovieRepository.getInstance(remoteDataSource,localDataSource,appExecutors);
    }
}
