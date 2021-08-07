package com.rizki.yourmovie.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.di.Injection;
import com.rizki.yourmovie.ui.detail.DetailViewModel;
import com.rizki.yourmovie.ui.favorite.FavoriteViewModel;
import com.rizki.yourmovie.ui.movie.MovieViewModel;
import com.rizki.yourmovie.ui.search.SearchViewModel;

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
        }else if (modelClass.isAssignableFrom(DetailViewModel.class)){
            return (T) new DetailViewModel(mMovieRepository);
        }else if (modelClass.isAssignableFrom(FavoriteViewModel.class)){
            return (T) new FavoriteViewModel(mMovieRepository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(mMovieRepository);
        }
        throw new IllegalArgumentException("unknown viewModel Class: "+ modelClass.getName());
    }
}
