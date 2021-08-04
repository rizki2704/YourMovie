package com.rizki.yourmovie.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.rizki.yourmovie.data.MovieRepository;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.vo.Resource;

public class DetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<Integer> movieId = new MutableLiveData<>();

    public DetailViewModel(MovieRepository mMovieRepository) {
        this.movieRepository = mMovieRepository;
    }

    public Integer getMovieId() {
        return movieId.getValue();
    }

    public void setMovieId(Integer movieId) {
        this.movieId.setValue(movieId);
    }

    public LiveData<Resource<MovieEntity>> getMovieDetails = Transformations.switchMap(movieId,
            mMovieId -> movieRepository.getMovieDetails(mMovieId));
}
