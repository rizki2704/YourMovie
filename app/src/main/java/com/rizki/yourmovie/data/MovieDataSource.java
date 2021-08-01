package com.rizki.yourmovie.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.vo.Resource;

public interface MovieDataSource {
    LiveData<Resource<PagedList<MovieEntity>>> getAllMovies(String sort);

    LiveData<Resource<MovieEntity>> getMovieDetails(Integer movieId);

    LiveData<PagedList<MovieEntity>> getFavoriteMovies();

    void setFavoriteMovies(MovieEntity movie, Boolean state);



}
