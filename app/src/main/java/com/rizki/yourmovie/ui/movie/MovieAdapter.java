package com.rizki.yourmovie.ui.movie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rizki.yourmovie.data.source.local.entity.MovieEntity;
import com.rizki.yourmovie.databinding.ItemListBinding;
import com.rizki.yourmovie.ui.detail.DetailActivity;

import org.jetbrains.annotations.NotNull;

import static com.rizki.yourmovie.utils.ApiData.IMAGE_URL;

public class MovieAdapter extends PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder> {
    MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieViewHolder holder, int position) {
        MovieEntity movie = getItem(position);
        if (movie != null){
            holder.bind(movie);
        }
    }

    private static DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull MovieEntity oldItem, @NonNull @NotNull MovieEntity newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull @NotNull MovieEntity oldItem, @NonNull @NotNull MovieEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        final ItemListBinding binding;

        MovieViewHolder(ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(MovieEntity movie) {
            binding.tvFilmTitle.setText(movie.getTitle());
            binding.tvRelease.setText(movie.getReleaseDate());
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.getId());
                itemView.getContext().startActivity(intent);

            });
            Glide.with(itemView.getContext())
                    .load(IMAGE_URL + movie.getPosterPath())
                    .into(binding.imgCover);
            binding.tvDeskripsi.setText(movie.getOverview());
        }
    }
}
