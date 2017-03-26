package com.nikita.movies_shmoovies

interface AppRouter {
  fun openMovieDetailsScreen()
  fun openTvDetailsScreen()
  fun openPersonDetailsScreen()
}

class BaseAppRouter(private val currentActivityProvider: CurrentActivityProvider) : AppRouter {
  override fun openMovieDetailsScreen() {
    throw UnsupportedOperationException("not implemented")
  }

  override fun openTvDetailsScreen() {
    throw UnsupportedOperationException("not implemented")
  }

  override fun openPersonDetailsScreen() {
    throw UnsupportedOperationException("not implemented")
  }
}