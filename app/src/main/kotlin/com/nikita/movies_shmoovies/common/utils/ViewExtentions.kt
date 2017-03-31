package com.nikita.movies_shmoovies.common.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.MOVIE_DB_IMAGE_BASE_URL
import com.squareup.picasso.Picasso

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

fun ImageView.load(image: String) = Picasso
        .with(this.context)
        .load(MOVIE_DB_IMAGE_BASE_URL + image)
        .fit()
        .centerCrop()
        .into(this)

fun ImageView.loadWithPlaceholder(image: String, placeholder: Int = R.drawable.mis_poster_placeholder) = Picasso
        .with(this.context)
        .load(MOVIE_DB_IMAGE_BASE_URL + image)
        .placeholder(placeholder)
        .error(placeholder)
        .fit()
        .centerCrop()
        .into(this)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}