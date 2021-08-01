package com.rizki.yourmovie.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;

import java.util.List;

@Dao
public interface MoviesDao {
    @RawQuery(observedEntities = MovieEntity.class)
    DataSource.Factory<Integer, MovieEntity> getMovies(SimpleSQLiteQuery query);

    @Query("SELECT * FROM movie_entities WHERE id = :id")
    LiveData<MovieEntity> getMovieById(Integer id);

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    DataSource.Factory<Integer, MovieEntity> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Update
    void updateMovie(MovieEntity movies);

}
