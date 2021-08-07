package com.rizki.yourmovie.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.local.entity.VideosEntity;

@Database(entities = {MovieEntity.class, VideosEntity.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();

    private static volatile MoviesDatabase INSTANCE;

    public static MoviesDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, "movies_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
