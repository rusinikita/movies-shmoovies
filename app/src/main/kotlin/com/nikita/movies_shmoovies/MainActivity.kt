package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.posters.PostersFragment

class MainActivity : AppCompatActivity() {
  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when (item.itemId) {
      R.id.navigation_movies -> {
        showPostersFragment(PostersFragment.Type.Movies)
        true
      }
      R.id.navigation_tv_shows -> {
        showPostersFragment(PostersFragment.Type.TvShows)
        true
      }
      R.id.navigation_people -> {
        showPostersFragment(PostersFragment.Type.People)
        true
      }
      else -> false
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findView(R.id.toolbar))
    setTitle(R.string.app_name)
    val navigation = findView<BottomNavigationView>(R.id.navigation)
    // TODO Лень обходить баг с запоминанием выбранной вкладки
    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    if (savedInstanceState == null) {
      showPostersFragment(PostersFragment.Type.Movies)
    }
  }

  private fun showPostersFragment(type: PostersFragment.Type) {
    supportFragmentManager
      .beginTransaction()
      .add(R.id.fragment_container, PostersFragment.create(type))
      .commit()
  }
}
