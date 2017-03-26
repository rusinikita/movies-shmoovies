package com.nikita.movies_shmoovies.common.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

abstract class BaseMvpPresenter<View : MvpView> : MvpPresenter<View>() {
  private val job: Job = Job() // parent job for canceling all jobs on destroy
  protected val context = job + UI

  /**
   * Function for operations in presenter coroutines context
   */
  protected fun launch(start: Boolean = true, block: suspend CoroutineScope.() -> Unit) = launch(context, start, block)

  protected fun <T> async(start: Boolean = true, block: suspend CoroutineScope.() -> T) = async(context, start, block)

  protected fun <DATA> launchLce(view: LceView<DATA>, pullToRefresh: Boolean = false, dataProvider: suspend CoroutineScope.() -> DATA) = launch {
    view.switchToLoading(pullToRefresh)
    try {
      val data = async(block = dataProvider).await()
      view.setContent(data)
    } catch (e: Exception) {
      // TODO error handling
      view.switchToError(ErrorDesc(errorText = e.message))
    }
  }

  override fun onDestroy() {
    job.cancel()
  }
}