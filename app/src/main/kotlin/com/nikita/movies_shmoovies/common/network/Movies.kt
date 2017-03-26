package com.nikita.movies_shmoovies.common.network

data class MovieListResponse(val page: Int,
                             val results: List<Movie>,
                             val total_pages: Int,
                             val total_results: Int) {
  data class Movie(val id: String,
                   val title: String,
                   val poster_path: String,
                   val release_date: String)
}