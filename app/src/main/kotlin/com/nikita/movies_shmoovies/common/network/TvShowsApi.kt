package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsApi {
  @GET("/3/tv/on_the_air")
  fun getGetOnTheAir(@Query("api_key") apiKey: String): Call<ListResponse<TvShow>>
}

class TvShowsService(api: TvShowsApi): BaseService<TvShowsApi>(api) {
  fun getOnTheAir() = api.getGetOnTheAir(BuildConfig.API_KEY).executeUnsafe()
}