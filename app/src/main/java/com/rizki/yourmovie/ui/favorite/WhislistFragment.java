package com.rizki.yourmovie.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rizki.yourmovie.R;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.databinding.FragmentWhislistBinding;
import com.rizki.yourmovie.viewmodel.ViewModelFactory;


public class WhislistFragment extends Fragment {
    private FragmentWhislistBinding binding;
    private FavoriteViewModel viewModel;
    private FavoriteAdapter adapter;

    public WhislistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWhislistBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemTouchHelper.attachToRecyclerView(binding.rvMovie);
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            viewModel = new ViewModelProvider(this, factory).get(FavoriteViewModel.class);
            adapter = new FavoriteAdapter();
            viewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movies->{
                if(movies.size() != 0){
                 binding.tvFavorite.setVisibility(View.GONE);
                }
                adapter.submitList(movies);
            });
            binding.rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvMovie.setHasFixedSize(true);
            binding.rvMovie.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                int swipedPosition = viewHolder.getAdapterPosition();
                MovieEntity movieEntity = adapter.getSwipedData(swipedPosition);
                viewModel.setFavoriteMovie(movieEntity);
                Snackbar snackbar = Snackbar.make(getView(), R.string.message_undo, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.message_ok, v -> viewModel.setFavoriteMovie(movieEntity));
                snackbar.show();
            }
        }
    });
}