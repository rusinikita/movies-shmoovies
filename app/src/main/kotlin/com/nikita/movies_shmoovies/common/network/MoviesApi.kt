package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
  @GET("/3/movie/upcoming")
  fun getGetUpcoming(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>
}

class MoviesService(api: MoviesApi): BaseService<MoviesApi>(api) {
  fun getUpcoming() = api.getGetUpcoming(BuildConfig.API_KEY).executeUnsafe()
}