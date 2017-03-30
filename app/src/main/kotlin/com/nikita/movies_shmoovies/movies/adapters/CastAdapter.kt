package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.movies.MovieInformation

class CastAdapter(val data: List<MovieInformation.CrewAndCast.Cast>) : RecyclerView.Adapter<CastHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        return CastHolder(parent.inflate(R.layout.cast_item))
    }
    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        holder.name.text = data[position].name
        holder.character.text = data[position].character
    }
    override fun getItemCount() = data.size
}

class CastHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findView<ImageView>(R.id.actor_photo)
    val name = view.findView<TextView>(R.id.actor_name)
    val character = view.findView<TextView>(R.id.actor_character)
}
