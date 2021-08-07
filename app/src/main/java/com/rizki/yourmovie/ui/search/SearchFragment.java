package com.rizki.yourmovie.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.rizki.yourmovie.R;
import com.rizki.yourmovie.databinding.FragmentSearchBinding;
import com.rizki.yourmovie.ui.movie.MovieViewModel;
import com.rizki.yourmovie.viewmodel.ViewModelFactory;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private SearchAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(SearchViewModel.class);
        adapter = new SearchAdapter();
        performSearch();
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMovie.setHasFixedSize(true);
        binding.rvMovie.setAdapter(adapter);
    }

    private void performSearch() {
        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchMovies(v.getText().toString()).observe(getViewLifecycleOwner(), movies -> {
                    switch (movies.status) {
                        case SUCCESS:
                            if (movies.data != null) {
                                adapter.submitList(movies.data);
                                adapter.notifyDataSetChanged();
                            }
                            break;
                        case ERROR:
                            break;
                        case LOADING:
                            break;
                    }
                });
                return true;
            }
            return false;
        });
    }
}