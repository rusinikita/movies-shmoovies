package com.nikita.movies_shmoovies.common.mvp

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Base ElceView interface to provide Error, Loading, and Content states
 * With Moxy strategy [StateStrategyType] states will replace each other
 *
 * NOTE: All inherited interfaces should be rooted,
 * otherwise Moxy won't import them in the generated classes
 */

@StateStrategyType(SingleStateStrategy::class)
interface LceView<in D> {
  fun setContent(content: D)
  fun switchToLoadingState(pullToRefresh: Boolean)
  fun switchToErrorState(errorDesc: ErrorDesc)
}

/**
 * Describes error content and error action button behavior
 *
 * @param returnAction overrides action on back button click in error state
 */
data class ErrorDesc(
  @DrawableRes
  val imageRes: Int = -1,
  @StringRes
  val titleRes: Int = -1,
  val titleText: String? = null,
  @StringRes
  val descriptionRes: Int = -1,
  val descriptionText: String? = null,
  @StringRes
  val buttonTextRes: Int = -1,
  val buttonAction: (() -> Unit)? = null,
  val returnAction: (() -> Unit)? = null
)