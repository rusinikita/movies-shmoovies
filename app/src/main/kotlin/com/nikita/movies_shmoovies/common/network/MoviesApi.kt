package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
  @GET("/3/movie/upcoming")
  fun getUpcoming(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>

  @GET("/3/movie/popular")
  fun getPopular(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>

  @GET("/3/movie/top_rated")
  fun getTop(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>
}

class MoviesService(api: MoviesApi): BaseService<MoviesApi>(api) {
  fun getUpcoming() = api.getUpcoming(BuildConfig.API_KEY).executeUnsafe()
  fun getPopular() = api.getPopular(BuildConfig.API_KEY).executeUnsafe()
  fun getTop() = api.getTop(BuildConfig.API_KEY).executeUnsafe()
}