package com.nikita.movies_shmoovies.posters

import com.nikita.movies_shmoovies.common.network.MoviesService

interface PostersInteractor {
  fun getMovies(): PostersPM
  fun getTvShows(): PostersPM
  fun getPeople(): PostersPM
}

data class PostersPM(val posters: List<Poster>) {
  data class Poster(val id: String,
                    val title: String,
                    val image: String)
}

class BasePostersInteractor(val moviesService: MoviesService): PostersInteractor {
  override fun getMovies(): PostersPM {
    val posters = moviesService.getUpcoming().results
      .map { PostersPM.Poster(it.id, it.title, it.poster_path) }
    return PostersPM(posters)
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