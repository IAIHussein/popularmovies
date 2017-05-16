package com.iaihussein.popularmovies;

import android.net.Uri;

import com.iaihussein.popularmovies.database.DBSQLiteHelper;

/**
 * Created by iaihussein on 10/21/16.
 */
public class Var {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.iaihussein.popularmovies";
    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(DBSQLiteHelper.TABLE_NAME).build();
    final static String RESULTS = "results", URL_BASE = "https://api.themoviedb.org/3/movie/",
            KEY = "?api_key=7e4e2d265e8739afc0fd5716ea3c4f50",//the api key
            REVIEWS = "/reviews",
            VIDEOS = "/videos",
            URL_POPULAR = URL_BASE + "popular" + KEY,
            URL_TOP = URL_BASE + "top_rated" + KEY,
            URL_IMAGE = "http://image.tmdb.org/t/p/w185",
            ARG_EXTRA = "movie",
            URL_YOUTUBE = "http://www.youtube.com/watch?v=",
            DETAIL = "DETAIL",
            MOVIE_TYPE = "movie",
            REVIEW_TYPE = "review",
            TRAILER_TYPE = "trailer";
}
