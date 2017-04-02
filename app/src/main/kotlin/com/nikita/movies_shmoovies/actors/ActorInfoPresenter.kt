package com.nikita.movies_shmoovies.actors

import com.arellomobile.mvp.InjectViewState
import com.nikita.movies_shmoovies.common.mvp.BaseMvpPresenter

@InjectViewState
class ActorInfoPresenter(val interactor: IActorInfoInteractor, val actorId:String) : BaseMvpPresenter<ActorInfoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadContent()
    }

    fun loadContent() {
        launchLce(viewState, true){
            interactor.getActorInfo(actorId)
        }
    }
}
