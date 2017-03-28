package com.nikita.movies_shmoovies

import com.nikita.movies_shmoovies.common.network.NetworkModule
import com.nikita.movies_shmoovies.posters.BasePostersInteractor
import com.nikita.movies_shmoovies.posters.PostersInteractor

class AppModule(val currentActivityProvider: CurrentActivityProvider, private val networkModule: NetworkModule) {
  val appRouter: AppRouter by lazy { BaseAppRouter(currentActivityProvider) }

  val postersInteractor: PostersInteractor by lazy { BasePostersInteractor(networkModule.moviesService) }
}