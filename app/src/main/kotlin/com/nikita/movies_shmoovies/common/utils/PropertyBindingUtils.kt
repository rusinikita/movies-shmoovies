package com.nikita.movies_shmoovies.common.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View
import java.lang.IllegalStateException
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface UnbindableProperty {
  fun unbind()
}

/**
 * Holds references on all lifecycle related properties to prevent memory leaks.
 *
 * [viewContainer] - view container, where view can be found if it not null.
 * It will help to control binding lifecycle
 * (for example, use binded views in the [Fragment.onCreateView]).
 */
class PropertyBinder() {
  private val bindedProperties = LinkedList<UnbindableProperty>()
  var viewContainer: View? = null

  fun register(managed: UnbindableProperty) {
    synchronized(bindedProperties) {
      bindedProperties.add(managed)
    }
  }

  fun reset() {
    // Synchronize to make sure the timing of a unbind() call and new inits do not collide
    synchronized(bindedProperties) {
      bindedProperties.forEach { it.unbind() }
      bindedProperties.clear()
      viewContainer = null
    }
  }
}

private class ViewProperty<in T, VIEW_TYPE>(private val binder: PropertyBinder,
                                            val initializer: (container: View?) -> VIEW_TYPE)
  : ReadOnlyProperty<T, VIEW_TYPE>, UnbindableProperty {

  @Volatile var lazyHolder = initLazyProperty()

  override fun getValue(thisRef: T, property: KProperty<*>): VIEW_TYPE = lazyHolder.value

  override fun unbind() {
    lazyHolder = initLazyProperty()
  }

  fun initLazyProperty(): Lazy<VIEW_TYPE> {
    return lazy {
      binder.register(this)
      initializer.invoke(binder.viewContainer)
    }
  }
}

private abstract class Property<in T, TYPE>(private val binder: PropertyBinder)
  : ReadWriteProperty<T, TYPE>, UnbindableProperty {

  @Volatile protected var lazyHolder = initLazyProperty()

  override fun unbind() {
    lazyHolder = initLazyProperty()
  }

  override fun setValue(thisRef: T, property: KProperty<*>, value: TYPE) {
    lazyHolder = initLazyProperty(value)
  }

  protected fun initLazyProperty(value: TYPE? = null): Lazy<TYPE?> {
    return lazy {
      if (value != null) {
        binder.register(this)
      }
      value
    }
  }
}

private class PropertyReq<in T, TYPE : Any>(binder: PropertyBinder) : Property<T, TYPE>(binder) {

  override fun getValue(thisRef: T, property: KProperty<*>): TYPE {
    return lazyHolder.value ?: propertyNotInitialized()
  }
}

private class PropertyOpt<in T, TYPE : Any>(binder: PropertyBinder) : Property<T, TYPE?>(binder) {

  override fun getValue(thisRef: T, property: KProperty<*>) = lazyHolder.value
}

private fun viewNotFound(id: Int): Nothing =
  throw IllegalStateException("View with ID $id not found. Probable causes: " +
    "viewContainer of PropertyBinder didn't set; " +
    "tried to use property after end of view lifecycle.")

private fun propertyNotInitialized(): Nothing =
  throw IllegalStateException("PropertyReq is null. Probable causes: " +
    "property didn't initialize before using;" +
    "tried to use property after end of view lifecycle.")

/**
 * Function to bind views in the fragments instead of **lateinit** directive
 * to avoid Context leaks (for example, after rotation).
 *
 * Binded views can be used in the [Fragment.onCreateView] if [PropertyBinder.viewContainer] was set,
 * or only **after** [Fragment.onCreateView] otherwise (for example, in the [Fragment.onAttach]).
 *
 * Without setting [PropertyBinder.viewContainer] will be thrown IllegalStateException.
 *
 * **NOTE**: [PropertyBinder] should be created as soon as possible and shouldn't be null or lazy
 * **NOTE**: binder.unbind() should be invoked before content view will be destroy.
 * For example, on [Fragment.onDestroyView].
 */
@Suppress("UNCHECKED_CAST")
fun <VIEW_TYPE : View> Fragment.bindView(propertyBinder: PropertyBinder,
                                         @IdRes id: Int): ReadOnlyProperty<Fragment, VIEW_TYPE> {
  return ViewProperty(propertyBinder, { viewContainer ->
    (viewContainer ?: view)?.findViewById(id) as? VIEW_TYPE ?: viewNotFound(id)
  })
}

@Suppress("UNCHECKED_CAST")
fun <VIEW_TYPE : View> Fragment.bindViewOpt(propertyBinder: PropertyBinder,
                                         @IdRes id: Int): ReadOnlyProperty<Fragment, VIEW_TYPE?> {
  return ViewProperty(propertyBinder, { viewContainer ->
    (viewContainer ?: view)?.findViewById(id) as? VIEW_TYPE
  })
}

/**
 * Functions to bind views in activities. Just for consistency with fragments
 */
@Suppress("UNCHECKED_CAST")
fun <VIEW_TYPE : View> Activity.bindView(propertyBinder: PropertyBinder,
                                         @IdRes id: Int): ReadOnlyProperty<Activity, VIEW_TYPE> {
  return ViewProperty(propertyBinder, {
    findViewById(id) as? VIEW_TYPE ?: viewNotFound(id)
  })
}

@Suppress("UNCHECKED_CAST")
fun <VIEW_TYPE : View> Activity.bindViewOpt(propertyBinder: PropertyBinder,
                                         @IdRes id: Int): ReadOnlyProperty<Activity, VIEW_TYPE?> {
  return ViewProperty(propertyBinder, {
    findViewById(id) as? VIEW_TYPE
  })
}

/**
 * Overloaded [bindView] fun for Views.
 */
@Suppress("UNCHECKED_CAST")
fun <VIEW_TYPE : View> View.bindView(propertyBinder: PropertyBinder,
                                     @IdRes id: Int): ReadOnlyProperty<View, VIEW_TYPE> {
  return ViewProperty(propertyBinder, { viewContainer ->
    (viewContainer ?: this).findViewById(id) as? VIEW_TYPE ?: viewNotFound(id)
  })
}

/**
 * Function to bind properties in the lifecycle related classes
 * instead of **lateinit** directive to avoid Context leaks
 * (for example, after rotation).
 */
fun <TYPE : Any> bindProperty(propertyBinder: PropertyBinder): ReadWriteProperty<Any, TYPE> {
  return PropertyReq(propertyBinder)
}

/**
 * Same as [bindProperty] but with nullable support
 */
fun <TYPE : Any> bindPropertyOpt(propertyBinder: PropertyBinder): ReadWriteProperty<Any, TYPE?> {
  return PropertyOpt(propertyBinder)
}