package com.nikita.movies_shmoovies.common.utils

import android.app.Activity
import android.view.View

inline fun <reified T : View> Activity.findView(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Activity.findViewOptional(id: Int): T? = findViewById(id) as? T