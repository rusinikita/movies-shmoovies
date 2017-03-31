package com.nikita.movies_shmoovies.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.common.mvp.BaseMvpActivity
import com.nikita.movies_shmoovies.common.utils.load
import com.nikita.movies_shmoovies.common.utils.loadWithPlaceholder
import com.nikita.movies_shmoovies.movies.adapters.CastAdapter
import com.nikita.movies_shmoovies.movies.adapters.CrewAdapter
import com.nikita.movies_shmoovies.movies.adapters.GenresAdapter
import kotlinx.android.synthetic.main.activity_movies_info.*
import kotlinx.android.synthetic.main.movie_info.*
import kotlinx.android.synthetic.main.movie_info_about.*
import kotlinx.android.synthetic.main.movie_info_header.*

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

    override fun setContent(content: MovieInformation) {
        super.setContent(content)
        content_view.visibility = View.VISIBLE

        movie_poster.loadWithPlaceholder(content.movieDetails.poster_path, R.drawable.mis_poster_placeholder)
        movie_background.loadWithPlaceholder(content.movieDetails.backdrop_path, R.drawable.mis_back_placeholder)
        movie_title.text = content.movieDetails.original_title
        movie_year.text = content.movieDetails.release_date.substring(0,4)
        movie_overview.text = content.movieDetails.overview
        movie_budget.text = content.movieDetails.budget.toString()
        movie_revenue.text = content.movieDetails.revenue.toString()
        movie_runtime.text = content.movieDetails.runtime.toString()
        movie_release.text = content.movieDetails.release_date.replace("-",".")
        movie_status.text = content.movieDetails.status
        movie_url.text = content.movieDetails.homepage
        movie_crew_recycler.adapter = CrewAdapter(content.crewAndCast.crew)
        movie_cast_recycler.adapter = CastAdapter(content.crewAndCast.cast)
        movie_genres_recycler.adapter = GenresAdapter(content.movieDetails.genres)

        // todo: try-catch with custom error
        if(!content.movieDetails.homepage.isEmpty()){
            movie_url.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(content.movieDetails.homepage)))
            }
        }
    }
}
