package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.BuildConfig
import com.nikita.movies_shmoovies.movies.MovieInformation
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
  @GET("/3/movie/upcoming")
  fun getUpcoming(
          @Query("api_key") apiKey: String,
          @Query("page") page: Int): Call<ListResponse<Movie>>

  @GET("/3/movie/popular")
  fun getPopular(
          @Query("api_key") apiKey: String,
          @Query("page") page: Int): Call<ListResponse<Movie>>

  @GET("/3/movie/top_rated")
  fun getTop(@Query("api_key") apiKey: String): Call<ListResponse<Movie>>

  @GET("/3/movie/{id}")
  fun getMovieDetails(@Path("id") id: String,
                      @Query("api_key") apiKey: String): Call<MovieInformation.MovieDetails>

  @GET("/3/movie/{id}/credits")
  fun getMovieCredits(@Path("id") id: String,
                      @Query("api_key") apiKey: String) : Call<MovieInformation.CrewAndCast>
}

class MoviesService(api: MoviesApi): BaseService<MoviesApi>(api) {
  /*TODO: Bug with upcoming*/
  fun getUpcoming(page: Int = 7) = api.getUpcoming(BuildConfig.API_KEY, page).executeUnsafe()
  fun getPopular(page: Int = 4) = api.getPopular(BuildConfig.API_KEY, page).executeUnsafe()
  fun getTop() = api.getTop(BuildConfig.API_KEY).executeUnsafe()
  fun getMovieDetails(id: String) = api.getMovieDetails(id, BuildConfig.API_KEY).executeUnsafe()
  fun getMovieCredits(id: String) = api.getMovieCredits(id, BuildConfig.API_KEY).executeUnsafe()
}