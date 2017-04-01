package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.common.utils.load
import com.nikita.movies_shmoovies.common.utils.loadWithPlaceholder
import com.nikita.movies_shmoovies.movies.MovieInformation

class CastAdapter(val data: List<MovieInformation.CrewAndCast.Cast>) : RecyclerView.Adapter<CastHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        return CastHolder(parent.inflate(R.layout.cast_item))
    }
    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        holder.name.text = data[position].name
        holder.character.text = data[position].character
        try {
            holder.image.loadWithPlaceholder(data[position].profile_path, R.drawable.mis_actor_placeholder)
        } catch (e: Exception){
            Log.e("CastAdapter", e.message)
        }
    }
    override fun getItemCount() = data.size
}

class CastHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findView<ImageView>(R.id.actor_photo)
    val name = view.findView<TextView>(R.id.actor_name)
    val character = view.findView<TextView>(R.id.actor_character)
}
