package com.rizki.yourmovie.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.vo.Resource;

public class MovieViewModel extends ViewModel {
    private MovieRepository movieRepository;
    public MovieViewModel(MovieRepository mMovieRepository){
        this.movieRepository = mMovieRepository;
    }
    public LiveData<Resource<PagedList<MovieEntity>>> getMovies(String sort){
        return movieRepository.getAllMovies(sort);
    }
}
