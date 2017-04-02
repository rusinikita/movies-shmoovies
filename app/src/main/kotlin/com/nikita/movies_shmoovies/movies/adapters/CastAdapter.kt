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
import com.nikita.movies_shmoovies.common.utils.loadWithPlaceholder
import com.nikita.movies_shmoovies.movies.MovieInformation.CrewAndCast.Cast

class CastAdapter(val data: List<RecyclerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ERROR_VIEW = 0
    val CAST_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            CAST_VIEW -> return CastHolder(parent.inflate(R.layout.cast_item))
            ERROR_VIEW -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
            else -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            CAST_VIEW -> {
                (holder as CastHolder).name.text = (data[position] as Cast).name
                holder.character.text = (data[position] as Cast).character
                try {
                    holder.image.loadWithPlaceholder((data[position] as Cast).profile_path, R.drawable.mis_actor_placeholder)
                } catch (e: Exception){
                    Log.e("CastAdapter", e.message)
                }
            }
            ERROR_VIEW -> (holder as ErrorHolder).errorTxt.text = (data[position] as ErrorContent).errorTxt
        }

    }
    override fun getItemViewType(position: Int): Int {
        if (data[position] is RegularItem) return CAST_VIEW
        if (data[position] is ErrorItem) return ERROR_VIEW
        else return ERROR_VIEW
    }
    override fun getItemCount() = data.size
}

class CastHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findView<ImageView>(R.id.actor_photo)
    val name = view.findView<TextView>(R.id.actor_name)
    val character = view.findView<TextView>(R.id.actor_character)
}
