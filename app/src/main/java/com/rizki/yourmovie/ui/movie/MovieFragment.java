package com.rizki.yourmovie.ui.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rizki.yourmovie.databinding.FragmentMovieBinding;
import com.rizki.yourmovie.viewmodel.ViewModelFactory;

import org.jetbrains.annotations.NotNull;

import static com.rizki.yourmovie.utils.SortUtils.RATING_TERTINGGI;

public class MovieFragment extends Fragment {
    private FragmentMovieBinding binding;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            MovieViewModel viewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
            MovieAdapter movieAdapter = new MovieAdapter();
            viewModel.getMovies(RATING_TERTINGGI).observe(getViewLifecycleOwner(), movies -> {
                if (movies != null) {
                    switch (movies.status) {

                        case SUCCESS:
                            binding.progressBar.setVisibility(View.GONE);
                            movieAdapter.submitList(movies.data);
                            break;
                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });
            binding.rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvMovie.setHasFixedSize(true);
            binding.rvMovie.setAdapter(movieAdapter);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}