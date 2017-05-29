package com.nikita.movies_shmoovies

import android.app.Activity
import android.app.Application
import android.os.Bundle

typealias CurrentActivityProvider = () -> Activity

class ActivityChangeDetector(private var currentActivity: Activity) : Application.ActivityLifecycleCallbacks {
  val currentActivityProvider: CurrentActivityProvider = { currentActivity }

  override fun onActivityPaused(activity: Activity) {}

  override fun onActivityResumed(activity: Activity) {}

  override fun onActivityStarted(activity: Activity) {}

  override fun onActivityDestroyed(activity: Activity) {}

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

  override fun onActivityStopped(activity: Activity) {}

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    currentActivity = activity
  }
}