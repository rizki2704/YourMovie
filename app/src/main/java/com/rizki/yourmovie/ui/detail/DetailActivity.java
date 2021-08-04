package com.rizki.yourmovie.ui.detail;

import static com.rizki.yourmovie.utils.ApiData.IMAGE_URL;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.rizki.yourmovie.R;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.databinding.ActivityDetailBinding;
import com.rizki.yourmovie.viewmodel.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    private ActivityDetailBinding binding;
    DetailViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory).get(DetailViewModel.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            Integer movieId = extras.getInt(EXTRA_MOVIE);
            viewModel.setMovieId(movieId);
            viewModel.getMovieDetails.observe(this, movie->{
                switch (movie.status){

                    case SUCCESS:
                        if (movie.data != null){
                            populateData(movie.data);
                        }
                        break;
                    case ERROR:
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void populateData(MovieEntity movie){
        binding.tvDetailTitle.setText(movie.getTitle());
        binding.tvDetailRelease.setText(movie.getReleaseDate());
        binding.tvDetailDescription.setText(movie.getOverview());
        Glide.with(this)
                .load(IMAGE_URL + movie.getPosterPath())
                .into(binding.tvDetailImage);
        Glide.with(this)
                .load(IMAGE_URL + movie.getBackdropPath())
                .into(binding.tvImageBanner);
    }
}