package com.rizki.yourmovie.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.local.entity.MoviesWithVideos;
import com.rizki.yourmovie.data.source.local.entity.VideosEntity;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movie_entities")
    DataSource.Factory<Integer, MovieEntity> getMovies();

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE id = :id")
    LiveData<MoviesWithVideos> getMovieById(Integer id);

    @Query("SELECT * FROM videos_entities")
    DataSource.Factory<Integer, VideosEntity> getVideos();

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    DataSource.Factory<Integer, MovieEntity> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVideos(List<VideosEntity> videos);

    @Update
    void updateMovie(MovieEntity movies);

}
