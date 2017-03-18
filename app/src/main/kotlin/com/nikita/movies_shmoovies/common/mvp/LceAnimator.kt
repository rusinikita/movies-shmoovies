package com.nikita.movies_shmoovies.common.mvp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.nikita.movies_shmoovies.common.utils.dpToPx

/**
 * Little helper class for animating content, error and loading view
 *
 * @author Hannes Dorfmann
 */
object LceAnimator {

  /**
   * Show the loading view. No animations, because sometimes loading things is pretty fast (i.e.
   * retrieve data from memory cache).
   */
  fun showLoading(loadingView: View, contentView: View, errorView: View) {

    contentView.visibility = View.GONE
    errorView.visibility = View.GONE
    loadingView.visibility = View.VISIBLE
  }

  /**
   * Shows the error view instead of the loading view
   */
  fun showErrorView(loadingView: View, contentView: View, errorView: View) {

    contentView.visibility = View.GONE

    // Not visible yet, so animate the view in
    val set = AnimatorSet()
    val errorIn = ObjectAnimator.ofFloat(errorView, "alpha", 1f)
    val loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f)

    set.playTogether(errorIn, loadingOut)
    set.duration = 200

    set.addListener(object : AnimatorListenerAdapter() {

      override fun onAnimationStart(animation: Animator) {
        super.onAnimationStart(animation)
        errorView.visibility = View.VISIBLE
      }

      override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        loadingView.visibility = View.GONE
        loadingView.alpha = 1f // For future showLoading calls
      }
    })

    set.start()
  }

  /**
   * Display the content instead of the loadingView
   */
  fun showContent(loadingView: View, contentView: View,
                  errorView: View) {

    if (contentView.visibility == View.VISIBLE) {
      // No Changing needed, because contentView is already visible
      errorView.visibility = View.GONE
      loadingView.visibility = View.GONE
    } else {

      errorView.visibility = View.GONE

      val translateDp = 40
      // Not visible yet, so animate the view in
      val set = AnimatorSet()
      val contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f)
      val contentTranslateIn = ObjectAnimator.ofFloat(contentView, "translationY",
        loadingView.context.dpToPx(translateDp.toFloat()).toFloat(), 0f)

      val loadingFadeOut = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f)
      val loadingTranslateOut = ObjectAnimator.ofFloat(loadingView, "translationY", 0f,
        -loadingView.context.dpToPx(translateDp.toFloat()).toFloat())

      set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut, loadingTranslateOut)
      set.duration = 500

      set.addListener(object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator) {
          contentView.translationY = 0f
          loadingView.translationY = 0f
          contentView.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animator) {
          loadingView.visibility = View.GONE
          loadingView.alpha = 1f // For future showLoading calls
          contentView.translationY = 0f
          loadingView.translationY = 0f
        }
      })

      set.start()
    }
  }
}