package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView

interface RecyclerItem

interface ErrorItem : RecyclerItem
interface RegularItem : RecyclerItem

class ErrorHolder(view: View) : RecyclerView.ViewHolder(view) {
    val errorTxt = view.findView<TextView>(R.id.error_item)
}

data class ErrorContent(val errorTxt: String) : ErrorItem
