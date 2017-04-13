package com.nikita.movies_shmoovies.posters

import android.view.ViewGroup
import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.AppRouter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter
import com.nikita.movies_shmoovies.common.utils.layoutInflater
import com.nikita.movies_shmoovies.common.utils.load

@InjectViewState
class PostersPresenter(private val behavior: Behavior) : BaseMvpPresenter<PostersView>() {

  var data: MutableList<PostersPM.Poster> = mutableListOf()
  var itemClickAction: ((PostersPM.Poster) -> Unit)? = null

  override fun onFirstViewAttach() = loadContent(pullToRefresh = true)

  private fun loadContent(pullToRefresh: Boolean, pagination: Boolean = false) {
    launchLce(viewState, pullToRefresh, pagination) {
      behavior.loadContent(pagination)
    }
  }

  fun onRefreshTriggered() = loadContent(pullToRefresh = true)

  fun onPosterClick(id: String) = behavior.onPosterClick(id)

  abstract class Behavior {
    abstract fun loadContent(pagination: Boolean): PostersPM
    abstract fun onPosterClick(id: String)
  }

  // Functions for RecyclerAdapter
  fun onBindViewHolder(holder: PostersFragment.MoviesViewHolder, position: Int){
    val item = data[position]

    if (item.image != null) {
      holder.image.load(item.image)
    }

    if (position == data.size-1 && behavior !is TopPostersBehavior){
      loadContent(pullToRefresh = false, pagination = true)
    }
  }
  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostersFragment.MoviesViewHolder {
    val view = parent.context.layoutInflater.inflate(R.layout.posters_item, parent, false)
    val holder = PostersFragment.MoviesViewHolder(view)
    view.setOnClickListener { itemClickAction?.invoke(data[holder.adapterPosition]) }
    return holder
  }
  fun getItemCount() = data.size
}

// Behavior classes
class PopularPostersBehavior(private val postersInteractor: PostersInteractor,
                             private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM {
    if (!pagination) {postersInteractor.resetPopularPages()}
    return postersInteractor.getPopular(pagination)
  }

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }
}

class TopPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM {
    if (!pagination) {postersInteractor.resetPages()}
    return postersInteractor.getTopMovies()
  }

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }
}

class UpcomingPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM {
    if (!pagination ) {postersInteractor.resetUpcomingPages()}
    return postersInteractor.getUpcomingMovies(pagination)
  }

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }
}

class TvPostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getTvShows()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen("315837")
  }
}

class PeoplePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getPeople()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen("315837")
  }
}