package com.rizki.yourmovie.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.vo.Resource;

public class SearchViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public SearchViewModel(MovieRepository mMovieRepository) {
        this.movieRepository = mMovieRepository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> searchMovies(String movieName){
        return movieRepository.searchMovies(movieName);
    }
}
