package com.nikita.movies_shmoovies.posters

import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.AppRouter
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter
import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit

@InjectViewState
class PostersPresenter(private val behavior: Behavior) : BaseMvpPresenter<PostersView>() {

  override fun onFirstViewAttach() = loadContent(pullToRefresh = false)

  private fun loadContent(pullToRefresh: Boolean) {
    launchLce(viewState, pullToRefresh) {
      delay(3, TimeUnit.SECONDS)
      behavior.loadContent()
    }
  }

  fun onRefreshTriggered() = loadContent(pullToRefresh = true)

  fun onPosterClick(id: String) = behavior.onPosterClick(id)

  abstract class Behavior {
    abstract fun loadContent(): PostersPM
    abstract fun onPosterClick(id: String)
  }
}

class MoviePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getMovies()

  override fun onPosterClick(id: String) {
    throw UnsupportedOperationException("not implemented")
  }
}

class TvPostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getTvShows()

  override fun onPosterClick(id: String) {
    throw UnsupportedOperationException("not implemented")
  }
}

class PeoplePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getPeople()

  override fun onPosterClick(id: String) {
    throw UnsupportedOperationException("not implemented")
  }
}