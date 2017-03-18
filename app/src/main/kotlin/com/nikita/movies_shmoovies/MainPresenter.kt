package com.nikita.movies_shmoovies

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

  private fun job() = async(UI) {
    delay(4, TimeUnit.SECONDS)
  }

  fun onTabSelected() {
    viewState.switchToLoading(false)
    launch(UI) {
      job().await()
      viewState.setContent("Job finished!")
    }
  }
}