package com.rizki.yourmovie.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rizki.yourmovie.data.source.local.LocalDataSource;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.remote.ApiResponse;
import com.rizki.yourmovie.data.source.remote.RemoteDataSource;
import com.rizki.yourmovie.data.source.remote.response.movie.GenresItem;
import com.rizki.yourmovie.data.source.remote.response.movie.MovieDetailsResponse;
import com.rizki.yourmovie.data.source.remote.response.movie.MoviesItem;
import com.rizki.yourmovie.utils.AppExecutors;
import com.rizki.yourmovie.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements MovieDataSource {
    private volatile static MovieRepository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    private MovieRepository(@NonNull RemoteDataSource remoteDataSource, @NonNull LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    public static MovieRepository getInstance(RemoteDataSource remoteData, LocalDataSource localDataSource, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                INSTANCE = new MovieRepository(remoteData, localDataSource, appExecutors);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getAllMovies(String sort) {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MoviesItem>>(appExecutors) {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getMovies(sort), config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<MoviesItem>>> createCall() {
                return remoteDataSource.getAllMovies();
            }

            @Override
            protected void saveCallResult(List<MoviesItem> data) {
                ArrayList<MovieEntity> movies = new ArrayList<>();
                for (MoviesItem res : data) {
                    MovieEntity movie = new MovieEntity(
                            res.getId(),
                            res.getTitle(),
                            res.getPosterPath(),
                            res.getReleaseDate(),
                            res.getBackdropPath(),
                            "",
                            res.getVoteAverage(),
                            res.getOverview(),
                            false
                    );
                    movies.add(movie);
                }
                localDataSource.insertMovies(movies);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieEntity>> getMovieDetails(Integer movieId) {
        return new NetworkBoundResource<MovieEntity, MovieDetailsResponse>(appExecutors) {

            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                return localDataSource.getMovieById(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEntity data) {
                return (data != null) && data.getGenres() == null;
            }

            @Override
            protected LiveData<ApiResponse<MovieDetailsResponse>> createCall() {
                return remoteDataSource.getMovieDetails(movieId.toString());
            }

            @Override
            protected void saveCallResult(MovieDetailsResponse data) {
                StringBuilder sb = new StringBuilder();
                for (GenresItem genre : data.getGenres()) {
                    sb.append(genre.getName()).append(", ");

                }
                MovieEntity movie = new MovieEntity(
                        data.getId(),
                        data.getTitle(),
                        data.getPosterPath(),
                        data.getReleaseDate(),
                        data.getBackdropPath(),
                        sb.toString(),
                        data.getVoteAverage(),
                        data.getOverview(),
                        false
                );
                localDataSource.updateMovie(movie, false);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<PagedList<MovieEntity>> getFavoriteMovies() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getFavoriteMovies(), config).build();

    }

    @Override
    public void setFavoriteMovies(MovieEntity movie, Boolean state) {
        appExecutors.diskIO().execute(()->localDataSource.setFavoriteMovie(movie, state));
    }
}
