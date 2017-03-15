package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

  private var mTextMessage: TextView? = null

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

    when (item.itemId) {
      R.id.navigation_home -> {
        mTextMessage!!.setText(R.string.title_home)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        mTextMessage!!.setText(R.string.title_dashboard)

        launch(UI) {
          repeat(10) {
            mTextMessage?.text = "Repeat $it"
            delay(1, TimeUnit.SECONDS)
          }
        }

        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        mTextMessage!!.setText(R.string.title_notifications)

        val async = async(UI) {
          delay(5, TimeUnit.SECONDS)
          "Async done!"
        }

        launch(UI) {
          mTextMessage?.text = async.await()
        }

        return@OnNavigationItemSelectedListener true
      }
    }

    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mTextMessage = findViewById(R.id.message) as TextView
    val navigation = findViewById(R.id.navigation) as BottomNavigationView
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
  }
}
