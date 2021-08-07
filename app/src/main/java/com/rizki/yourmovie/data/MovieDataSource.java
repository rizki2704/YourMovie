package com.rizki.yourmovie.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.local.entity.MoviesWithVideos;
import com.rizki.yourmovie.data.source.local.entity.VideosEntity;
import com.rizki.yourmovie.data.source.remote.ApiResponse;
import com.rizki.yourmovie.data.source.remote.response.movie.MoviesItem;
import com.rizki.yourmovie.vo.Resource;

import java.util.List;

public interface MovieDataSource {
    LiveData<Resource<PagedList<MovieEntity>>> getAllMovies(String sort);

    LiveData<Resource<MoviesWithVideos>> getMovieDetails(Integer movieId);

    LiveData<Resource<PagedList<VideosEntity>>> getAllVideos(Integer movieId);

    LiveData<PagedList<MovieEntity>> getFavoriteMovies();

    LiveData<Resource<PagedList<MovieEntity>>> searchMovies(String movieName);

    void setFavoriteMovies(MovieEntity movie, Boolean state);



}
