package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.movies.MovieInformation

class CrewAdapter(val data: List<MovieInformation.CrewAndCast.Crew>) : RecyclerView.Adapter<CrewHolder>() {
    override fun onBindViewHolder(holder: CrewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.job.text = data[position].job
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        return CrewHolder(parent.inflate(R.layout.crew_item))
    }
    override fun getItemCount() = data.size
}

class CrewHolder(view: View) : RecyclerView.ViewHolder(view){
    val name = view.findView<TextView>(R.id.crew_name)
    val job = view.findView<TextView>(R.id.crew_job)
}
