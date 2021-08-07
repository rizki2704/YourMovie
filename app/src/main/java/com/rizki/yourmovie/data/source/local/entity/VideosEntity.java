package com.rizki.yourmovie.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "videos_entities")
public class VideosEntity {
    public VideosEntity(String id, String trailerKey, String movieId) {
        this.id = id;
        this.trailerKey = trailerKey;
        this.movieId = movieId;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "trailerKey")
    private String trailerKey;

    @ColumnInfo(name = "movieId")
    private String movieId;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
