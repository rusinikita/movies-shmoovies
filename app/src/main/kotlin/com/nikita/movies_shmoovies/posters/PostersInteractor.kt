package com.nikita.movies_shmoovies.posters

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

class BasePostersInteractor(): PostersInteractor {
  override fun getMovies(): PostersPM {
    return createFakePosters("Movies")
  }

  override fun getTvShows(): PostersPM {
    return createFakePosters("Shows")
  }

  override fun getPeople(): PostersPM {
    return createFakePosters("People")
  }

  fun createFakePosters(type: String): PostersPM {
    return PostersPM(List(40, { PostersPM.Poster(id = it.toString(), title = "$type$it", image = "/tUMdinO528RqibbkKIAIRoGKq0g.jpg") }))
  }
}