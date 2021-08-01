package com.rizki.yourmovie.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.di.Injection;
import com.rizki.yourmovie.ui.movie.MovieViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final MovieRepository mMovieRepository;
    private ViewModelFactory(MovieRepository movieRepository){
        mMovieRepository = movieRepository;

    }
    public static ViewModelFactory getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(MovieViewModel.class)){
            return (T) new MovieViewModel(mMovieRepository);
        }
        throw new IllegalArgumentException("unknown viewModel Class: "+ modelClass.getName());
    }
}
