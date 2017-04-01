package com.nikita.movies_shmoovies.posters

import com.nikita.movies_shmoovies.common.network.MoviesService

interface PostersInteractor {
  fun getUpcomingMovies(pagination: Boolean = false): PostersPM
  fun getTvShows(): PostersPM
  fun getTopMovies(): PostersPM
  fun getPeople(): PostersPM
  fun getPopular(pagination: Boolean = false): PostersPM

  fun resetPages()
  fun resetPopularPages()
  fun resetUpcomingPages()
}

data class PostersPM(val posters: List<Poster>) {
  data class Poster(val id: String,
                    val title: String,
                    val image: String?)
}

class BasePostersInteractor(val moviesService: MoviesService): PostersInteractor {
  var popularPageCounter: Int = 1
  var upcomingPageCounter: Int = 1

  override fun resetPages() {
    popularPageCounter = 1
    upcomingPageCounter = 1
  }

  override fun resetUpcomingPages() {
    upcomingPageCounter = 1
  }

  override fun resetPopularPages() {
    upcomingPageCounter = 1
  }

  override fun getPopular(pagination: Boolean): PostersPM {
    val popularPosters = moviesService.getPopular(popularPageCounter).results
            .map { PostersPM.Poster(it.id, it.title, it.poster_path) }

    if (pagination) {popularPageCounter++}
    else if (!pagination && popularPageCounter == 1) {popularPageCounter++}
    else {popularPageCounter = 1}

    return PostersPM(popularPosters)
  }

  override fun getUpcomingMovies(pagination: Boolean): PostersPM {

    val posters = moviesService.getUpcoming(upcomingPageCounter).results
      .map { PostersPM.Poster(it.id, it.title, it.poster_path) }

    if (pagination) {upcomingPageCounter++}
    else if (!pagination && upcomingPageCounter == 1) {upcomingPageCounter++}
    else {upcomingPageCounter = 1}

    return PostersPM(posters)
  }

  override fun getTopMovies(): PostersPM {
    val topPosters = moviesService.getTop().results.subList(0,10)
            .map { PostersPM.Poster(it.id, it.title, it.poster_path) }
    return PostersPM(topPosters)
  }

  override fun getTvShows(): PostersPM {
    return createFakePosters("Shows")
  }

  override fun getPeople(): PostersPM {
    return createFakePosters("People")
  }

  private fun createFakePosters(type: String): PostersPM {
    return PostersPM(List(40, { PostersPM.Poster(id = it.toString(), title = "$type$it", image = "/tUMdinO528RqibbkKIAIRoGKq0g.jpg") }))
  }
}