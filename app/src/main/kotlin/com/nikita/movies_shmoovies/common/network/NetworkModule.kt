package com.nikita.movies_shmoovies.common.network

import com.nikita.movies_shmoovies.common.network.ActorApi
import com.nikita.movies_shmoovies.common.network.ActorInfoService
import com.nikita.movies_shmoovies.common.DEFAULT_CONNECT_TIMEOUT
import com.nikita.movies_shmoovies.common.MOVIE_DB_API_BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class NetworkModule {
  val moviesService: MoviesService by lazy {
    MoviesService(retrofit.create(MoviesApi::class.java))
  }

  val actorInfoService: ActorInfoService by lazy {
    ActorInfoService(retrofit.create(ActorApi::class.java))
  }

  private val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
      .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .writeTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .build()
  }
  private val moshi: Moshi by lazy {
    Moshi.Builder().build()
  }
  private val retrofit: Retrofit by lazy {

    Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .client(okHttpClient)
      .baseUrl(MOVIE_DB_API_BASE_URL)
      .build()
  }
}