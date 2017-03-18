package com.nikita.movies_shmoovies.common.mvp

import android.view.ViewGroup

interface LceBehavior {
  fun init(rootView: ViewGroup)
  fun switchToContent()
  fun switchToLoading(pullToRefresh: Boolean)
  fun switchToError(errorDesc: ErrorDesc)
  fun handleBack(): Boolean
}

/**
 * Adds progress and error view in rootView on initialization
 *
 * War
 */
class BaseLceBehavior : LceBehavior {

  override fun init(rootView: ViewGroup) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun switchToContent() {
    throw UnsupportedOperationException("not implemented")
  }

  override fun switchToLoading(pullToRefresh: Boolean) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun switchToError(errorDesc: ErrorDesc) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun handleBack(): Boolean {
    throw UnsupportedOperationException("not implemented")
  }
}