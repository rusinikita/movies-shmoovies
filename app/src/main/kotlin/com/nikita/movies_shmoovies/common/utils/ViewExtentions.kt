package com.nikita.movies_shmoovies.common.utils

import android.view.View
import android.view.ViewGroup

/**
 * Can be used to remove focus from input fields on activity/controller opening
 */
fun View.stealFocus() {
  isFocusableInTouchMode = true
  isFocusable = true
  requestFocus()
}

var View.isVisible: Boolean
  get() = this.visibility == View.VISIBLE
  set(value) {
    this.visibility = if (value) View.VISIBLE else View.GONE
  }

inline fun <reified T : View> View.findView(id: Int): T = findViewById(id) as T
inline fun <reified T : View> View.findViewOptional(id: Int): T? = findViewById(id) as? T

