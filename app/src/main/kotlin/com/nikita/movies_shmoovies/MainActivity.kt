package com.nikita.movies_shmoovies

import android.support.design.widget.BottomNavigationView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.common.mvp.BaseMvpActivity
import com.nikita.movies_shmoovies.common.mvp.LceView
import com.nikita.movies_shmoovies.common.utils.findView

interface MainView: LceView<String>

class MainActivity : BaseMvpActivity<String>(), MainView {
  override val layout: Int = R.layout.activity_main

  @InjectPresenter
  lateinit var presenter: MainPresenter

  @ProvidePresenter
  fun providePresenter() = MainPresenter()

  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when (item.itemId) {
      R.id.navigation_home -> {
        presenter.onTabSelected()
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        presenter.onTabSelected()
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        presenter.onTabSelected()
        return@OnNavigationItemSelectedListener true
      }
    }

    false
  }

  override fun setContent(content: String) {
    super.setContent(content)
  }

  override fun initView() {
    val navigation = findView<BottomNavigationView>(R.id.navigation)
    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
  }
}
