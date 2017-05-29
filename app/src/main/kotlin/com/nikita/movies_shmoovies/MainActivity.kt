package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.MoviesFragment
import com.nikita.movies_shmoovies.posters.PostersFragment

class MainActivity : AppCompatActivity() {
  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when (item.itemId) {
      R.id.navigation_movies -> {
        showMoviesFragment()
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
    supportActionBar?.elevation = 0f
    setTitle(R.string.app_name)
    val navigation = findView<BottomNavigationView>(R.id.navigation)
    // TODO Лень обходить баг с запоминанием выбранной вкладки
    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    if (savedInstanceState == null) {
      showMoviesFragment()
    }
  }

  private fun showPostersFragment(type: PostersFragment.Type) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, PostersFragment.create(type))
      .commit()
  }

  private fun showMoviesFragment() {
    supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MoviesFragment())
            .commit()
  }
}
