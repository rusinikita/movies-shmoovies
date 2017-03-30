package com.nikita.movies_shmoovies.movies

import com.arellomobile.mvp.MvpView

interface MoviesInfoView : MvpView {
    fun setViews(movieInformation: MovieInformation)
}
