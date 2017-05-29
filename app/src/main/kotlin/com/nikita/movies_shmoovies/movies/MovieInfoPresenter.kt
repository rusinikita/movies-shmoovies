package com.nikita.movies_shmoovies.movies

import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter

@InjectViewState
class MovieInfoPresenter(val interactor: MovieInfoInteractor, val id: String) : BaseMvpPresenter<MoviesInfoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadContent()
    }

    fun loadContent() {
        launchLce(viewState, true){
            interactor.getMovieInformation(id)
        }
    }
}
