package com.nikita.movies_shmoovies

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nikita.movies_shmoovies.common.mvp.ErrorDesc
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

  override fun onFirstViewAttach() {
    viewState.setContent("Start!")
  }

  private fun job() = async(UI) {
    delay(2, TimeUnit.SECONDS)
    if (Math.random() > 0.5) {
      throw IllegalStateException()
    }
  }

  fun onTabSelected() {
    viewState.switchToLoading(false)
    launch(UI) {
      try {
        job().await()
        viewState.setContent("Job finished!")
      } catch (e: Exception) {
        viewState.switchToError(ErrorDesc(errorText = "opop", buttonAction = { onTabSelected() }, buttonTextRes = R.string.app_name))
      }
    }
  }
}