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

  var data: List<PostersPM.Poster> = emptyList()
  var itemClickAction: ((PostersPM.Poster) -> Unit)? = null

  override fun onFirstViewAttach() = loadContent(pullToRefresh = false)

  private fun loadContent(pullToRefresh: Boolean) {
    launchLce(viewState, pullToRefresh) {
      behavior.loadContent()
    }
  }

  fun onRefreshTriggered() = loadContent(pullToRefresh = true)

  fun onPosterClick(id: String) = behavior.onPosterClick(id)

  abstract class Behavior {
    abstract fun loadContent(): PostersPM
    abstract fun onPosterClick(id: String)
  }

  // Functions for RecyclerAdapter
  fun onBindViewHolder(holder: PostersFragment.MoviesViewHolder, position: Int){
    val item = data[position]
    holder.image.load(item.image)
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
  override fun loadContent(): PostersPM = postersInteractor.getPopular()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen()
  }
}

class TopPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getTopMovies()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen()
  }
}

class UpcomingPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(): PostersPM = postersInteractor.getUpcomingMovies()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen()
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