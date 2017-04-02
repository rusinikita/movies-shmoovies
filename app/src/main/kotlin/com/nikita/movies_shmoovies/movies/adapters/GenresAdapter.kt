package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.movies.MovieInformation.MovieDetails.Genre

class GenresAdapter(val data: List<RecyclerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ERROR_VIEW = 0
    val GENRES_VIEW = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            GENRES_VIEW -> (holder as GenreHolder).genre.text = (data[position] as Genre).name
            ERROR_VIEW -> (holder as ErrorHolder).errorTxt.text = (data[position] as ErrorContent).errorTxt
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            GENRES_VIEW -> return GenreHolder(parent.inflate(R.layout.genre_item))
            ERROR_VIEW -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
            else -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
        }

    }
    override fun getItemViewType(position: Int): Int {
        if (data[position] is RegularItem) return GENRES_VIEW
        if (data[position] is ErrorItem) return ERROR_VIEW
        else return ERROR_VIEW
    }
    override fun getItemCount() = data.size
}

class GenreHolder(view: View) : RecyclerView.ViewHolder(view){
    val genre = view.findView<TextView>(R.id.movie_genre)
}
