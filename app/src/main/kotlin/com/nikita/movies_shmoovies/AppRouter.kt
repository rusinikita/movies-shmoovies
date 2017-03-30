package com.nikita.movies_shmoovies

import android.content.Intent
import com.nikita.movies_shmoovies.movies.MoviesInfoActivity

interface AppRouter {
  fun openMovieDetailsScreen(id: String)
  fun openTvDetailsScreen()
  fun openPersonDetailsScreen()
}

class BaseAppRouter(private val currentActivityProvider: CurrentActivityProvider) : AppRouter {
  override fun openMovieDetailsScreen(id: String) {
    val context = currentActivityProvider.invoke().baseContext
    context.startActivity(Intent(context, MoviesInfoActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra("id", id))
  }

  override fun openTvDetailsScreen() {
    throw UnsupportedOperationException("not implemented")
  }

  override fun openPersonDetailsScreen() {
    throw UnsupportedOperationException("not implemented")
  }
}