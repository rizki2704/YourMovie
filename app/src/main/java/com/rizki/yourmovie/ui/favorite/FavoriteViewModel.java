package com.rizki.yourmovie.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;

public class FavoriteViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public FavoriteViewModel(MovieRepository mMovieRepository) {
        this.movieRepository = mMovieRepository;
    }
    public LiveData<PagedList<MovieEntity>> getFavoriteMovies(){
        return movieRepository.getFavoriteMovies();
    }
    void setFavoriteMovie(MovieEntity movieEntity){
        final boolean state = !movieEntity.getFavorite();
        movieRepository.setFavoriteMovies(movieEntity, state);
    }

}
