package com.rizki.yourmovie.ui.detail;

import static com.rizki.yourmovie.utils.ApiData.IMAGE_URL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.rizki.yourmovie.R;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.data.source.local.entity.MoviesWithVideos;
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
        MaterialFavoriteButton favorite = (MaterialFavoriteButton) binding.imgFavorite;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            Integer movieId = extras.getInt(EXTRA_MOVIE);
            viewModel.setMovieId(movieId);

            viewModel.getVideos.observe(this, video -> {
                switch (video.status) {

                    case SUCCESS:
                        if (video.data != null) {
                            Log.d("TRAILER", video.data.toString());
                        }
                        break;
                    case ERROR:
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            });

            viewModel.getMovieDetails.observe(this, movie->{
                switch (movie.status){
                    case SUCCESS:
                        Log.d("MOVIE_DETAILS", movie.data.movieEntity.getTitle());
                        if (movie.data != null){
                            populateData(movie.data);
                            boolean state = movie.data.movieEntity.getFavorite();
                            favorite.setFavorite(state);
                            favorite.setOnFavoriteChangeListener(
                                    (buttonView, favorite1) -> viewModel.setFavoriteMovie()
                            );
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

    private void populateData(MoviesWithVideos movie){
        binding.tvDetailTitle.setText(movie.movieEntity.getTitle());
        binding.tvDetailRelease.setText(movie.movieEntity.getReleaseDate());
        binding.tvDetailDescription.setText(movie.movieEntity.getOverview());
        if (!movie.videosEntities.isEmpty()) {
            binding.btnTrailer.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movie.videosEntities.get(0).getTrailerKey()));
                startActivity(intent);
            });
        }
        Glide.with(this)
                .load(IMAGE_URL + movie.movieEntity.getPosterPath())
                .into(binding.tvDetailImage);
        Glide.with(this)
                .load(IMAGE_URL + movie.movieEntity.getBackdropPath())
                .into(binding.tvImageBanner);
    }

}