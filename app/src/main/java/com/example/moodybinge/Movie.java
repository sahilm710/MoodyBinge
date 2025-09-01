package com.example.moodybinge;

public class Movie {
    private final int tmdbId;
    private final String title;
    private final String posterPath;
    private String imdbId;
    private final String genreName;

    public Movie(int tmdbId, String title, String posterPath, String genreName) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.posterPath = posterPath;
        this.genreName = genreName;
    }

    public int getTmdbId() { return tmdbId; }
    public String getTitle() { return title; }
    public String getPosterPath() { return posterPath; }
    public String getGenreName() { return genreName; }

    public String getPosterUrl() {
        if (posterPath == null || posterPath.isEmpty()) return null;
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getTmdbPageUrl() {
        return "https://www.themoviedb.org/movie/" + tmdbId;
    }

    public String getImdbId() { return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }

    public String getImdbUrl() {
        return (imdbId == null || imdbId.isEmpty())
                ? null
                : "https://www.imdb.com/title/" + imdbId + "/";
    }
}


