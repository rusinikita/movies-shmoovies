package com.nikita.movies_shmoovies.movies

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.movies.adapters.CastAdapter
import com.nikita.movies_shmoovies.movies.adapters.CrewAdapter
import com.nikita.movies_shmoovies.movies.adapters.GenresAdapter
import kotlinx.android.synthetic.main.movie_info.*
import kotlinx.android.synthetic.main.movie_info_about.*
import kotlinx.android.synthetic.main.movie_info_header.*

class MoviesInfoActivity : MvpAppCompatActivity(), MoviesInfoView{
    @InjectPresenter
    lateinit var presenter: MovieInfoPresenter

    @ProvidePresenter
    fun providePresenter(): MovieInfoPresenter {
        return MovieInfoPresenter(appModule.movieInfoInteractor)
    }

    lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_info)
        movieId = intent.extras.getString("id")
        movie_crew_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_cast_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movie_genres_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun setViews(movieInformation: MovieInformation) {
        movie_title.text = movieInformation.movieDetails.originalTitle
        movie_year.text = movieInformation.movieDetails.releaseDate
        movie_overview.text = movieInformation.movieDetails.overview
        movie_budget.text = movieInformation.movieDetails.budget.toString()
        movie_revenue.text = movieInformation.movieDetails.revenue.toString()
        movie_runtime.text = movieInformation.movieDetails.runtime.toString()
        movie_release.text = movieInformation.movieDetails.releaseDate
        movie_status.text = movieInformation.movieDetails.status
        movie_url.text = movieInformation.movieDetails.homepage
        movie_crew_recycler.adapter = CrewAdapter(movieInformation.crewAndCast.crew)
        movie_cast_recycler.adapter = CastAdapter(movieInformation.crewAndCast.cast)
        movie_genres_recycler.adapter = GenresAdapter(movieInformation.movieDetails.genres)

    }

}
