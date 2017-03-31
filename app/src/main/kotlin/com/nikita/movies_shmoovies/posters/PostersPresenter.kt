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

  fun resetPages(){
    behavior.resetPages()
  }

  abstract class Behavior {
    abstract fun loadContent(pagination: Boolean): PostersPM
    abstract fun onPosterClick(id: String)
    abstract fun resetPages()
  }

  // Functions for RecyclerAdapter
  fun onBindViewHolder(holder: PostersFragment.MoviesViewHolder, position: Int){
    val item = data[position]
    holder.image.load(item.image)

    if (position == data.size-1){
      loadContent(false, false)
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
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getPopular()


  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }

  override fun resetPages() = postersInteractor.resetPages()
}

class TopPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getTopMovies()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }

  override fun resetPages() = postersInteractor.resetPages()
}

class UpcomingPostersBehavior(private val postersInteractor: PostersInteractor,
                              private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getUpcomingMovies()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen(id)
  }

  override fun resetPages() = postersInteractor.resetPages()
}

class TvPostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getTvShows()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen("315837")
  }

  override fun resetPages() = postersInteractor.resetPages()
}

class PeoplePostersBehavior(private val postersInteractor: PostersInteractor,
                           private val router: AppRouter): PostersPresenter.Behavior() {
  override fun loadContent(pagination: Boolean): PostersPM = postersInteractor.getPeople()

  override fun onPosterClick(id: String) {
    router.openMovieDetailsScreen("315837")
  }

  override fun resetPages() = postersInteractor.resetPages()
}