package com.nikita.movies_shmoovies.posters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.layoutInflater
import com.nikita.movies_shmoovies.common.utils.load

class PostersAdapter : RecyclerView.Adapter<PostersAdapter.PosterHolder>() {
  var data: List<PostersPM.Poster> = emptyList()
  var itemClickAction: ((PostersPM.Poster) -> Unit)? = null

  override fun onBindViewHolder(holder: PosterHolder, position: Int) {
    val item = data[position]
    holder.image.load(item.image)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterHolder {
    val view = parent.context.layoutInflater.inflate(R.layout.posters_item, parent, false)
    val holder = PosterHolder(view)
    view.setOnClickListener { itemClickAction?.invoke(data[holder.adapterPosition]) }
    return holder
  }

  override fun getItemCount() = data.size

  class PosterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.findView<ImageView>(R.id.poster_image)
  }
}