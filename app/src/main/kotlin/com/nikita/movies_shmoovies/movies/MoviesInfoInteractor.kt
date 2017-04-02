package com.nikita.movies_shmoovies.movies

import com.nikita.movies_shmoovies.common.network.MoviesService
import com.nikita.movies_shmoovies.movies.adapters.RecyclerItem
import com.nikita.movies_shmoovies.movies.adapters.RegularItem
import kotlinx.coroutines.experimental.async

interface MovieInfoInteractor {
    fun getMovieInformation(id: String): MovieInformation
}

data class MovieInformation(val movieDetails: MovieDetails, val crewAndCast: CrewAndCast){

    data class MovieDetails(val original_title: String,
                            val backdrop_path: String?,
                            val poster_path: String?,
                            val overview: String,
                            val budget: Int,
                            val revenue: Int,
                            val runtime: Int,
                            val release_date: String,
                            val status: String,
                            val homepage: String,
                            val genres: List<Genre>?,
                            val vote_average: Float) {
        data class Genre(val name: String) : RegularItem
    }

    data class CrewAndCast(val cast: List<Cast>?,
                           val crew: List<Crew>?){
        data class Cast(val character: String,
                        val name: String,
                        val profile_path: String,
                        val id: Int) : RegularItem

        data class Crew(val name: String,
                        val job: String) : RegularItem
    }

}

class MoviesInfoInteractor(val moviesService: MoviesService) : MovieInfoInteractor {
    override fun getMovieInformation(id: String): MovieInformation {
        return MovieInformation(getMovieDetails(id), getMovieCredits(id))
    }

    fun getMovieDetails(id: String): MovieInformation.MovieDetails {
        return moviesService.getMovieDetails(id)
    }

    fun getMovieCredits(id: String): MovieInformation.CrewAndCast {
        return moviesService.getMovieCredits(id)
    }
}