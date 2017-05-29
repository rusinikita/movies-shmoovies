package com.nikita.movies_shmoovies.common.mvp

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.*
import com.nikita.movies_shmoovies.common.widgets.ErrorView

interface LceBehavior {
  fun init(rootView: ViewGroup)
  fun switchToContent()
  fun switchToLoading(pullToRefresh: Boolean)
  fun switchToError(errorDesc: ErrorDesc)
}

/**
 * Adds progress and error view in rootView on initialization
 */
class BaseLceBehavior(propertyBinder: PropertyBinder) : LceBehavior {
  private var contentView by bindProperty<View>(propertyBinder)
  private var progressBar by bindProperty<View>(propertyBinder)
  private var errorView by bindProperty<ErrorView>(propertyBinder)
  private var refreshView by bindPropertyOpt<SwipeRefreshLayout>(propertyBinder)

  override fun init(rootView: ViewGroup) {
    contentView = rootView.findView(R.id.content_view)
    progressBar = rootView.context.layoutInflater.inflate(R.layout.layout_progress, rootView, false)
    errorView = rootView.context.layoutInflater.inflate(R.layout.layout_error, rootView, false) as ErrorView
    refreshView = rootView.findViewOptional(R.id.refresh_view)

    rootView.addView(progressBar)
    rootView.addView(errorView)
  }

  override fun switchToContent() {
    refreshView?.isRefreshing = false
    LceAnimator.showContent(
      loadingView = progressBar,
      contentView = contentView,
      errorView = errorView)
  }

  override fun switchToLoading(pullToRefresh: Boolean) {
    if (!pullToRefresh) {
      LceAnimator.showLoading(
        loadingView = progressBar,
        contentView = contentView,
        errorView = errorView)
    }

    // otherwise the pull to refresh widget will already display a loading animation
  }

  override fun switchToError(errorDesc: ErrorDesc) {
    errorView.content = errorDesc
    LceAnimator.showErrorView(
      loadingView = progressBar,
      contentView = contentView,
      errorView = errorView)
  }
}