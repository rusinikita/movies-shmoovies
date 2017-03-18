package com.nikita.movies_shmoovies

import android.support.design.widget.BottomNavigationView
import android.widget.TextView
import com.nikita.movies_shmoovies.common.mvp.BaseMvpActivity

class MainActivity : BaseMvpActivity<Unit>() {
  override val layout: Int = R.layout.activity_main
  private var mTextMessage: TextView? = null

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when (item.itemId) {
      R.id.navigation_home -> {
        mTextMessage!!.setText(R.string.title_home)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        mTextMessage!!.setText(R.string.title_dashboard)

        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        mTextMessage!!.setText(R.string.title_notifications)

        return@OnNavigationItemSelectedListener true
      }
    }

    false
  }

  override fun initView() {
    mTextMessage = findViewById(R.id.message) as TextView
    val navigation = findViewById(R.id.navigation) as BottomNavigationView
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
  }
}
