package com.iaihussein.popularmovies;

/**
 * Created by iaihussein on 10/21/16.
 */
public class Var {
    final static String RESULTS = "results", URL_BASE = "https://api.themoviedb.org/3/movie/",
            KEY = "?api_key=7e4e2d265e8739afc0fd5716ea3c4f50",//the api key
            REVIEWS = "/reviews",
            VIDEOS = "/videos",
            URL_POPULAR = URL_BASE + "popular" + KEY,
            URL_TOP = URL_BASE + "top_rated" + KEY,
            URL_IMAGE = "http://image.tmdb.org/t/p/w185",
            ARG_EXTRA = "movie",
            URL_YOUTUBE = "http://www.youtube.com/watch?v=";

}
