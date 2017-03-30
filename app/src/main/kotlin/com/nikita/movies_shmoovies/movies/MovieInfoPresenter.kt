package com.nikita.movies_shmoovies.movies

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MovieInfoPresenter(val interactor: MovieInfoInteractor) : MvpPresenter<MoviesInfoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setupViews()
    }

    fun setupViews(){
        viewState.setViews(interactor.getMovieInformation())
    }
}
