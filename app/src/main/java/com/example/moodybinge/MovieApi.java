package com.example.moodybinge;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApi {

    private static final String TAG = "MovieApi";

    // 🔑 Your TMDB v3 key (injected via BuildConfig)
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String BASE = "https://api.themoviedb.org/3";

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    // ---- Callbacks ----
    public interface MovieListCallback {
        void onSuccess(List<Movie> movies);
        void onError(Exception e);
    }

    public interface ImdbCallback {
        void onSuccess(String imdbId);
        void onError(Exception e);
    }

    // ✅ Utility: check API key
    private static boolean isApiKeyValid() {
        return API_KEY != null && !API_KEY.trim().isEmpty();
    }

    // ✅ Fetch movies by genre
    public static void fetchMoviesByGenre(Integer genreId, String genreName, MovieListCallback cb) {
        if (!isApiKeyValid()) {
            cb.onError(new IllegalStateException("TMDB API key is missing! Please check local.properties / BuildConfig."));
            return;
        }

        try {
            StringBuilder url = new StringBuilder(BASE +
                    "/discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&page=1" +
                    "&api_key=" + API_KEY);

            if (genreId != null) {
                url.append("&with_genres=").append(genreId);
            }

            Log.d(TAG, "Requesting URL: " + url);

            Request req = new Request.Builder()
                    .url(url.toString())
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Network failure", e);
                    cb.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "HTTP error " + response.code() + " : " + response.message());
                        cb.onError(new IOException("HTTP " + response.code()));
                        return;
                    }
                    String body = response.body().string();
                    Log.d(TAG, "Response body: " + body);

                    DiscoverResponse dr = gson.fromJson(body, DiscoverResponse.class);
                    List<Movie> out = new ArrayList<>();
                    if (dr != null && dr.results != null) {
                        for (Result r : dr.results) {
                            String fullPosterUrl = r.posterPath != null
                                    ? "https://image.tmdb.org/t/p/w500" + r.posterPath
                                    : null;
                            out.add(new Movie(r.id, r.title, fullPosterUrl, genreName));
                        }
                    }
                    Log.d(TAG, "Parsed " + out.size() + " movies");
                    cb.onSuccess(out);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception in fetchMoviesByGenre", e);
            cb.onError(e);
        }
    }

    // ✅ Fetch IMDb ID
    public static void fetchImdbId(int tmdbId, ImdbCallback cb) {
        if (!isApiKeyValid()) {
            cb.onError(new IllegalStateException("TMDB API key is missing! Please check local.properties / BuildConfig."));
            return;
        }

        try {
            String url = BASE + "/movie/" + tmdbId + "/external_ids?api_key=" + API_KEY;

            Log.d(TAG, "Requesting IMDb ID URL: " + url);

            Request req = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Network failure", e);
                    cb.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "HTTP error " + response.code() + " : " + response.message());
                        cb.onError(new IOException("HTTP " + response.code()));
                        return;
                    }
                    String body = response.body().string();
                    Log.d(TAG, "IMDb Response body: " + body);

                    ExternalIds ids = gson.fromJson(body, ExternalIds.class);
                    cb.onSuccess(ids != null ? ids.imdbId : null);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception in fetchImdbId", e);
            cb.onError(e);
        }
    }

    // ---- Data classes ----
    static class DiscoverResponse {
        List<Result> results;
    }

    static class Result {
        int id;
        String title;
        @SerializedName("poster_path")
        String posterPath;
    }

    static class ExternalIds {
        @SerializedName("imdb_id")
        String imdbId;
    }
}
