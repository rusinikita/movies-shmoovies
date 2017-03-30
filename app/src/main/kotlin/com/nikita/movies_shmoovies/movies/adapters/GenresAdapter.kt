package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.movies.MovieInformation

class GenresAdapter(val data: List<MovieInformation.MovieDetails.Genre>) : RecyclerView.Adapter<GenreHolder>() {
    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        holder.genre.text = data[position].name
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        return GenreHolder(parent.inflate(R.layout.genre_item))
    }
    override fun getItemCount() = data.size
}

class GenreHolder(view: View) : RecyclerView.ViewHolder(view){
    val genre = view.findView<TextView>(R.id.movie_genre)
}
