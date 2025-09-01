package com.example.moodybinge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Spinner spinnerGenre;
    private Button btnPick, btnOpenImdb;
    private ImageView imgPoster;
    private TextView tvMovieName, tvGenre, tvCounter;

    private final Map<String, Integer> GENRES = new LinkedHashMap<>();
    private String lastGenreName = null;

    private List<Movie> currentList = new ArrayList<>();
    private int currentIndex = -1;
    private Movie currentMovie = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bind views
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        btnPick = view.findViewById(R.id.btnPick);
        btnOpenImdb = view.findViewById(R.id.btnOpenImdb);
        imgPoster = view.findViewById(R.id.imgPoster);
        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvGenre = view.findViewById(R.id.tvGenre);
        tvCounter = view.findViewById(R.id.tvCounter);

        // Setup genre spinner
        setupGenres();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>(GENRES.keySet())
        );
        spinnerGenre.setAdapter(adapter);

        // Button: Pick Movie
        btnPick.setOnClickListener(v -> pickMovie());

        // IMDb button
        btnOpenImdb.setOnClickListener(v -> openImdb());

        return view;
    }

    // ------------------------
    // Movie picker logic
    // ------------------------

    private void setupGenres() {
        GENRES.put("All", null);
        GENRES.put("Action", 28);
        GENRES.put("Adventure", 12);
        GENRES.put("Animation", 16);
        GENRES.put("Comedy", 35);
        GENRES.put("Crime", 80);
        GENRES.put("Documentary", 99);
        GENRES.put("Drama", 18);
        GENRES.put("Family", 10751);
        GENRES.put("Fantasy", 14);
        GENRES.put("History", 36);
        GENRES.put("Horror", 27);
        GENRES.put("Music", 10402);
        GENRES.put("Mystery", 9648);
        GENRES.put("Romance", 10749);
        GENRES.put("Sci-Fi", 878);
        GENRES.put("Thriller", 53);
        GENRES.put("War", 10752);
        GENRES.put("Western", 37);
    }

    private void pickMovie() {
        String selected = (String) spinnerGenre.getSelectedItem();
        Integer genreId = GENRES.get(selected);

        if (currentList.isEmpty() || lastGenreName == null || !lastGenreName.equals(selected)) {
            lastGenreName = selected;
            currentIndex = -1;
            currentList.clear();
            tvMovieName.setText("Loading…");
            tvGenre.setText("Genre: " + selected);
            Glide.with(this).load(R.drawable.placeholder).into(imgPoster);

            MovieApi.fetchMoviesByGenre(genreId, selected, new MovieApi.MovieListCallback() {
                @Override public void onSuccess(List<Movie> movies) {
                    if (getActivity() == null) return;
                    getActivity().runOnUiThread(() -> {
                        if (movies == null || movies.isEmpty()) {
                            Toast.makeText(getContext(), "No movies found for " + selected, Toast.LENGTH_SHORT).show();
                            tvMovieName.setText("—");
                            tvGenre.setText("—");
                            Glide.with(HomeFragment.this).load(R.drawable.placeholder).into(imgPoster);
                            return;
                        }
                        currentList = movies;
                        Collections.shuffle(currentList);
                        showNext();
                    });
                }

                @Override public void onError(Exception e) {
                    if (getActivity() == null) return;
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        tvMovieName.setText("Error loading movies");
                        Glide.with(HomeFragment.this).load(R.drawable.placeholder).into(imgPoster);
                    });
                }
            });
            return;
        }

        showNext();
    }

    private void showNext() {
        if (currentList == null || currentList.isEmpty()) return;

        currentIndex++;
        if (currentIndex >= currentList.size()) {
            currentIndex = 0;
            Toast.makeText(getContext(), "Looped through all movies in " + lastGenreName, Toast.LENGTH_SHORT).show();
        }

        currentMovie = currentList.get(currentIndex);
        tvMovieName.setText(currentMovie.getTitle());
        tvGenre.setText("Genre: " + currentMovie.getGenreName());
        tvCounter.setText("Movie " + (currentIndex + 1) + " of " + currentList.size());

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(150);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(250);

        imgPoster.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) { }
            @Override public void onAnimationRepeat(Animation animation) { }
            @Override public void onAnimationEnd(Animation animation) {
                String posterUrl = currentMovie.getPosterUrl();
                Glide.with(HomeFragment.this)
                        .load(posterUrl != null ? posterUrl : R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgPoster);
                imgPoster.startAnimation(fadeIn);
            }
        });
    }

    private void openImdb() {
        if (currentMovie == null) {
            Toast.makeText(getContext(), "Pick a movie first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentMovie.getImdbUrl() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentMovie.getImdbUrl())));
            return;
        }

        MovieApi.fetchImdbId(currentMovie.getTmdbId(), new MovieApi.ImdbCallback() {
            @Override public void onSuccess(String imdbId) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    if (imdbId != null && !imdbId.isEmpty()) {
                        currentMovie.setImdbId(imdbId);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentMovie.getImdbUrl())));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentMovie.getTmdbPageUrl())));
                    }
                });
            }

            @Override public void onError(Exception e) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Opening on TMDB (IMDb not found)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentMovie.getTmdbPageUrl())));
                });
            }
        });
    }
}
