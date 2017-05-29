package com.nikita.movies_shmoovies.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.movies.MovieInformation.CrewAndCast.Crew

class CrewAdapter(val data: List<RecyclerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ERROR_VIEW = 0
    val CREW_VIEW = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CREW_VIEW -> {
                (holder as CrewHolder).name.text = (data[position] as Crew).name
                holder.job.text = (data[position] as Crew).job}
            ERROR_VIEW -> {(holder as ErrorHolder).errorTxt.text = (data[position] as ErrorContent).errorTxt}
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            CREW_VIEW -> return CrewHolder(parent.inflate(R.layout.crew_item))
            ERROR_VIEW -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
            else -> return ErrorHolder(parent.inflate(R.layout.emty_error_item))
        }

    }
    override fun getItemViewType(position: Int): Int {
        if (data[position] is ErrorItem) return ERROR_VIEW
        else if (data[position] is RegularItem) return CREW_VIEW
        else return ERROR_VIEW
    }
    override fun getItemCount() = data.size
}

class CrewHolder(view: View) : RecyclerView.ViewHolder(view){
    val name = view.findView<TextView>(R.id.crew_name)
    val job = view.findView<TextView>(R.id.crew_job)
}
