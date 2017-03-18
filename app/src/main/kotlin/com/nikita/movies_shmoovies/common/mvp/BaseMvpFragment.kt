package com.nikita.movies_shmoovies.common.mvp

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.nikita.movies_shmoovies.common.utils.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * Base class for app fragments
 */
abstract class BaseMvpFragment<in D> : MvpAppCompatFragment(), LceView<D> {
  protected abstract val layout: Int
  protected val propertyBinder = PropertyBinder()
  protected open val elceBehavior: LceBehavior = BaseLceBehavior(propertyBinder)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val view = inflater.inflate(layout, container, false)
    propertyBinder.viewContainer = view
    if (view !is ViewGroup) throw UnsupportedOperationException("root view of fragment layout must be ViewGroup")
    elceBehavior.init(view)
    return view
  }

  override fun setContent(content: D) {
    elceBehavior.switchToContent()
  }

  override fun switchToLoading(pullToRefresh: Boolean) {
    elceBehavior.switchToLoading(pullToRefresh)
  }

  override fun switchToError(errorDesc: ErrorDesc) {
    elceBehavior.switchToError(errorDesc)
  }

  protected fun <VIEW_TYPE : View> bindView(@IdRes id: Int): ReadOnlyProperty<Fragment, VIEW_TYPE> = bindView(propertyBinder, id)
  protected fun <VIEW_TYPE : View> bindViewOpt(@IdRes id: Int): ReadOnlyProperty<Fragment, VIEW_TYPE?> = bindViewOpt(propertyBinder, id)
  protected fun <TYPE : Any> bindProperty(): ReadWriteProperty<Fragment, TYPE> = bindProperty(propertyBinder)
  protected fun <TYPE : Any> bindPropertyOpt(): ReadWriteProperty<Fragment, TYPE?> = bindPropertyOpt(propertyBinder)

  override fun onDestroyView() {
    propertyBinder.reset()
    super.onDestroyView()
  }
}