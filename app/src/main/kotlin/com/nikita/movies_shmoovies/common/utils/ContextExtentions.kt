package com.nikita.movies_shmoovies.common.utils

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater

fun Context.dpToPx(dp: Float): Int = (resources.displayMetrics.density * dp + 0.5).toInt()
fun Context.dpToPxF(dp: Float): Float = dpToPx(dp).toFloat()

@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int, theme: Resources.Theme? = null): Drawable? = ResourcesCompat.getDrawable(this.resources, drawableRes, theme)

val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)

fun Context.getThemeColor(@AttrRes attr: Int): Int = getThemeAttr(attr, { it.getColor(0, 0) })

private inline fun <T> Context.getThemeAttr(@AttrRes attr: Int, extractor: (TypedArray) -> T): T {
  val attrs = intArrayOf(attr)
  val typedArray = this.obtainStyledAttributes(attrs)
  val value: T = extractor(typedArray)
  typedArray.recycle()
  return value
}