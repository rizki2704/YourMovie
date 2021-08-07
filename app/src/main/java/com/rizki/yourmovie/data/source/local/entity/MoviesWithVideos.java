package com.rizki.yourmovie.data.source.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MoviesWithVideos {
    @Embedded public MovieEntity movieEntity;
    @Relation(
            parentColumn = "id",
            entityColumn = "movieId"
    )
    public List<VideosEntity> videosEntities;

}
