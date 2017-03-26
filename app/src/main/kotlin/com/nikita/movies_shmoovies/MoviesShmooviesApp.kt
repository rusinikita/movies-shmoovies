package com.nikita.movies_shmoovies

import android.app.Activity
import android.app.Application
import com.nikita.movies_shmoovies.common.network.NetworkModule

val Activity.appModule get() = (application as MoviesShmooviesApp).getAppModule(this)

class MoviesShmooviesApp : Application() {
  private var appModule: AppModule? = null

  fun getAppModule(activity: Activity) = appModule ?: initAppModule(activity)

  private fun initAppModule(activity: Activity): AppModule {
    val activityChangeDetector = ActivityChangeDetector(activity)
    registerActivityLifecycleCallbacks(activityChangeDetector)
    appModule = AppModule(
      currentActivityProvider = activityChangeDetector.currentActivityProvider,
      networkModule = NetworkModule())
    return appModule!!
  }
}