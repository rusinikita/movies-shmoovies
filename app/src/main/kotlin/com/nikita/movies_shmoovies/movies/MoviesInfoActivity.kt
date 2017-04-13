package com.nikita.movies_shmoovies.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.actors.ActorInfoActivity
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.common.mvp.BaseMvpActivity
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.common.utils.inflate
import com.nikita.movies_shmoovies.common.utils.loadWithPlaceholder
import com.nikita.movies_shmoovies.common.widgets.CircleDisplay
import com.nikita.movies_shmoovies.movies.adapters.*
import kotlinx.android.synthetic.main.activity_movies_info.*
import kotlinx.android.synthetic.main.mis_header.*
import kotlinx.android.synthetic.main.movie_info.*
import kotlinx.android.synthetic.main.movie_info_about.*

class MoviesInfoActivity : BaseMvpActivity<MovieInformation>(), MoviesInfoView{

    override val layout: Int
        get() = R.layout.activity_movies_info

    @InjectPresenter
    lateinit var presenter: MovieInfoPresenter

    @ProvidePresenter
    fun providePresenter(): MovieInfoPresenter {
        var id: String
        try {
            id = intent.extras.getString("id")
        } catch (e: Exception) {
            id = "315837"
        }
        return MovieInfoPresenter(appModule.movieInfoInteractor, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie_crew_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_cast_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_genres_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    override fun setContent(content: MovieInformation, pagination: Boolean) {
        super.setContent(content, pagination)
        content_view.visibility = View.VISIBLE

        if (content.movieDetails.poster_path != null){
            movie_poster.loadWithPlaceholder(content.movieDetails.poster_path, R.drawable.mis_poster_placeholder)
        }

        if (content.movieDetails.backdrop_path != null) {
            movie_background.loadWithPlaceholder(content.movieDetails.backdrop_path, R.drawable.mis_back_placeholder)
        }
        mis_toolbar.title = content.movieDetails.original_title
        movie_year.text = content.movieDetails.release_date.substring(0,4)
        movie_overview.text = content.movieDetails.overview
        movie_budget.text = content.movieDetails.budget.toString()
        movie_revenue.text = content.movieDetails.revenue.toString()
        movie_runtime.text = content.movieDetails.runtime.toString()
        movie_release.text = content.movieDetails.release_date.replace("-",".")
        movie_status.text = content.movieDetails.status
        movie_url.text = content.movieDetails.homepage

//        TODO: REFACTOR THIS
        if (content.crewAndCast.crew == null || content.crewAndCast.crew.isEmpty()){
            movie_crew_recycler.adapter = CrewAdapter(listOf(ErrorContent(resources.getString(R.string.empty_list_error))))
        } else {
            movie_crew_recycler.adapter = CrewAdapter(content.crewAndCast.crew)
        }

        if (content.crewAndCast.cast == null || content.crewAndCast.cast.isEmpty()){
            movie_cast_recycler.adapter = CastAdapter(listOf(ErrorContent(resources.getString(R.string.empty_list_error))))
        } else {
            movie_cast_recycler.adapter = CastAdapter(content.crewAndCast.cast)
        }

        if (content.movieDetails.genres == null || content.movieDetails.genres.isEmpty()){
            movie_genres_recycler.adapter = GenresAdapter(listOf(ErrorContent(resources.getString(R.string.empty_list_error))))
        } else {
            movie_genres_recycler.adapter = GenresAdapter(content.movieDetails.genres)
        }


        if(!content.movieDetails.homepage.isEmpty()){
            movie_url.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(content.movieDetails.homepage)))
            }
        }

        val userScore = findViewById(R.id.user_score) as CircleDisplay
        userScore.visibility = View.VISIBLE
        userScore.setAnimDuration(500)
        userScore.setValueWidthPercent(30f)
        userScore.setTextSize(14f)
        userScore.setColor(resources.getColor(R.color.TMDB_green))
        userScore.setDrawText(true)
        userScore.setDrawInnerCircle(true)
        userScore.setFormatDigits(0)
        userScore.setUnit("")
        userScore.setStepSize(0.5f)
        userScore.showValue(content.movieDetails.vote_average*10, 100f, true)
    }

    // Adapter class for Cast list
      inner class CastAdapter(val data: List<RecyclerItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                    (holder as CastHolder).name.text = (data[position] as MovieInformation.CrewAndCast.Cast).name
                    holder.character.text = (data[position] as MovieInformation.CrewAndCast.Cast).character
                    try {
                        holder.image.loadWithPlaceholder((data[position] as MovieInformation.CrewAndCast.Cast).profile_path, R.drawable.mis_actor_placeholder)
                    } catch (e: Exception){
                        Log.e("CastAdapter", e.message)
                    }
                    holder.root.setOnClickListener { startActivity(
                            Intent(this@MoviesInfoActivity, ActorInfoActivity::class.java)
                                    .putExtra("actor_id", (data[position] as MovieInformation.CrewAndCast.Cast).id)) }
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
        val root = view.findView<LinearLayout>(R.id.cast_root)
    }

}
