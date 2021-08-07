package com.rizki.yourmovie.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.local.entity.MoviesWithVideos;
import com.rizki.yourmovie.data.source.local.entity.VideosEntity;
import com.rizki.yourmovie.data.source.local.room.MoviesDao;
import com.rizki.yourmovie.utils.SortUtils;

import java.util.List;

public class LocalDataSource {
    private static LocalDataSource INSTANCE;
    private final MoviesDao mMoviesDao;

    private LocalDataSource(MoviesDao mMoviesDao){
        this.mMoviesDao = mMoviesDao;
    }

    public static LocalDataSource getInstance(MoviesDao moviesDao){
        if (INSTANCE == null){
            INSTANCE = new LocalDataSource(moviesDao);
        }
        return INSTANCE;
    }

    public DataSource.Factory<Integer, MovieEntity> getMovies(){
        return mMoviesDao.getMovies();
    }

    public DataSource.Factory<Integer, VideosEntity> getVideos(Integer id){
        return mMoviesDao.getVideos();
    }

    public LiveData<MoviesWithVideos> getMovieById(Integer id)  {
        return mMoviesDao.getMovieById(id);
    }

    public DataSource.Factory<Integer, MovieEntity>getFavoriteMovies(){
        return mMoviesDao.getFavoriteMovies();
    }
    public void insertMovies(List<MovieEntity> movies){
         mMoviesDao.insertMovies(movies);
    }

    public void insertVideos(List<VideosEntity> videos){
        mMoviesDao.insertVideos(videos);
    }

    public void setFavoriteMovie(MovieEntity movie, Boolean newState) {
        movie.setFavorite(newState);
        mMoviesDao.updateMovie(movie);
    }

    public void updateMovie(MovieEntity movie, Boolean newState) {
        movie.setFavorite(newState);
        mMoviesDao.updateMovie(movie);
    }
}
