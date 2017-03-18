package com.nikita.movies_shmoovies.common.mvp

import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.nikita.movies_shmoovies.common.utils.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * Base class for app activities
 */
abstract class BaseMvpActivity<in D> : MvpAppCompatActivity(), LceView<D> {
  protected abstract val layout: Int
  protected val propertyBinder = PropertyBinder()
  protected open val elceBehavior: LceBehavior = BaseLceBehavior(propertyBinder)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout)
    elceBehavior.init(findView(android.R.id.content))
    initView()
  }

  open fun initView() {}

  override fun setContent(content: D) {
    elceBehavior.switchToContent()
  }

  override fun switchToLoading(pullToRefresh: Boolean) {
    elceBehavior.switchToLoading(pullToRefresh)
  }

  override fun switchToError(errorDesc: ErrorDesc) {
    elceBehavior.switchToError(errorDesc)
  }

  protected fun <VIEW_TYPE : View> bindView(@IdRes id: Int): ReadOnlyProperty<Activity, VIEW_TYPE> = bindView(propertyBinder, id)
  protected fun <VIEW_TYPE : View> bindViewOpt(@IdRes id: Int): ReadOnlyProperty<Activity, VIEW_TYPE?> = bindViewOpt(propertyBinder, id)
  protected fun <TYPE : Any> bindProperty(): ReadWriteProperty<Activity, TYPE> = bindProperty(propertyBinder)
  protected fun <TYPE : Any> bindPropertyOpt(): ReadWriteProperty<Activity, TYPE?> = bindPropertyOpt(propertyBinder)

  override fun onDestroy() {
    propertyBinder.reset()
    super.onDestroy()
  }
}