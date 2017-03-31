package com.nikita.movies_shmoovies.movies

import com.nikita.movies_shmoovies.common.network.MoviesService
import kotlinx.coroutines.experimental.async

interface MovieInfoInteractor {
    fun getMovieInformation(id: String): MovieInformation
}

data class MovieInformation(val movieDetails: MovieDetails, val crewAndCast: CrewAndCast){

    data class MovieDetails(val original_title: String,
                            val backdrop_path: String,
                            val poster_path: String,
                            val overview: String,
                            val budget: Int,
                            val revenue: Int,
                            val runtime: Int,
                            val release_date: String,
                            val status: String,
                            val homepage: String,
                            val genres: List<Genre>) {
        data class Genre(val name: String)
    }

    data class CrewAndCast(val cast: List<Cast>,
                           val crew: List<Crew>){
        data class Cast(val character: String,
                        val name: String,
                        val profilePath: String)

        data class Crew(val name: String,
                        val job: String)
    }

}

class MoviesInfoInteractor(val moviesService: MoviesService) : MovieInfoInteractor {
    override fun getMovieInformation(id: String): MovieInformation {
        return MovieInformation(getMovieDetails(id), createFakeCrew())
    }

    //    TODO: https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md#android
    fun getMovieDetails(id: String): MovieInformation.MovieDetails {
        val data: MovieInformation.MovieDetails = moviesService.getMovieDetails(id)
        return data
    }

    fun createFakeCrew(): MovieInformation.CrewAndCast {
        return MovieInformation.CrewAndCast(
                List(10, { MovieInformation.CrewAndCast.Cast("Astronaut", "Ivan Dobsky", "234") }),
                List(10, { MovieInformation.CrewAndCast.Crew("Bobby", "Homeless") })
        )
    }
}