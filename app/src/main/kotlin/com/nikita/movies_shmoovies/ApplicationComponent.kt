package com.nikita.movies_shmoovies

import com.nikita.movies_shmoovies.posters.BasePostersInteractor
import com.nikita.movies_shmoovies.posters.PostersInteractor


object ApplicationComponent {
  val postersInteractor: PostersInteractor by lazy { BasePostersInteractor() }
}