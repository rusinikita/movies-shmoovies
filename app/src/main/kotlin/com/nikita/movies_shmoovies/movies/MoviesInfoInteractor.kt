package com.nikita.movies_shmoovies.movies

interface MovieInfoInteractor {
    fun getMovieInformation(): MovieInformation
}

data class MovieInformation(val movieDetails: MovieDetails, val crewAndCast: CrewAndCast){
    data class MovieDetails(val originalTitle: String,
                            val backDropPath: String,
                            val posterPath: String,
                            val overview: String,
                            val budget: Int,
                            val revenue: Int,
                            val runtime: Int,
                            val releaseDate: String,
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

class MoviesInfoInteractor : MovieInfoInteractor {
    override fun getMovieInformation(): MovieInformation {
        return MovieInformation(createFakeDetails(), createFakeCrew())
    }

    fun createFakeDetails(): MovieInformation.MovieDetails {
        return MovieInformation.MovieDetails(
                "Interstellar",
                "123qwe",
                "123qwe",
                "Nice movie! -Bob",
                900000000,
                999999999,
                169,
                "14.01.14",
                "Released",
                "http://interstellar.com",
                List(3, { MovieInformation.MovieDetails.Genre("Dramma") }))
    }

    fun createFakeCrew(): MovieInformation.CrewAndCast {
        return MovieInformation.CrewAndCast(
                List(10, { MovieInformation.CrewAndCast.Cast("Astronaut", "Ivan Dobsky", "234") }),
                List(10, { MovieInformation.CrewAndCast.Crew("Bobby", "Homeless") })
        )
    }
}