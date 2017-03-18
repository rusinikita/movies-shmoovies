package com.nikita.movies_shmoovies.utils

import android.content.Context

fun Context.dpToPx(dp: Float): Int = (resources.displayMetrics.density * dp + 0.5).toInt()
fun Context.dpToPxF(dp: Float): Float = dpToPx(dp).toFloat()