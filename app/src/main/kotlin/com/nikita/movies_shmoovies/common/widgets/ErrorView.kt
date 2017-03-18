package com.nikita.movies_shmoovies.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.mvp.ErrorDesc
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.findViewOptional
import com.nikita.movies_shmoovies.common.utils.isVisible
import com.nikita.movies_shmoovies.common.utils.layoutInflater

class ErrorView : LinearLayout {
  private val iconView: ImageView?
  private val errorTextView: TextView
  private val actionButton: TextView

  var content: ErrorDesc? = null
    set(value) {
      field = value
      if (value != null) {
        if (value.imageRes > 0) {
          iconView?.setImageResource(value.imageRes)
        }
        errorTextView.text = value.resolveErrorText(context)
        actionButton.isVisible = value.buttonAction != null
        if (value.buttonAction != null) {
          actionButton.setText(value.buttonTextRes)
          actionButton.setOnClickListener { value.buttonAction.invoke() }
        }
      }
    }

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    context.layoutInflater.inflate(R.layout.view_content_error, this, true)
    iconView = findViewOptional(R.id.error_icon)
    errorTextView = findView(R.id.error_text)
    actionButton = findView(R.id.error_action)
  }
}