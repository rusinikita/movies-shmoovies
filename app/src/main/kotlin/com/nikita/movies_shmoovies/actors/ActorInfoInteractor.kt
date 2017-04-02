package com.nikita.movies_shmoovies.actors

import com.nikita.movies_shmoovies.common.network.Movie
import com.nikita.movies_shmoovies.actors.ActorInfoScreen.ActorInfo.ActorMovies

data class ActorInfoScreen (val actorInfo: ActorInfo, val actorMovies: ActorInfo.ActorMovies) {
    data class ActorInfo(val name : String,
                         val birthday: String,
                         val gender: Int,
                         val homepage: String,
                         val biography: String,
                         val imdb_id: String,
                         val place_of_birth: String,
                         val profile_path: String?
    ) {
        data class ActorMovies(val cast: List<Movie>,
                               val crew: List<Movie>)
    }
}

interface IActorInfoInteractor {
    fun getActorInfo(actorId: String): ActorInfoScreen
}
class ActorInfoInteractor (val actorService: ActorInfoService) : IActorInfoInteractor {

    override fun getActorInfo(actorId: String): ActorInfoScreen {
        return ActorInfoScreen(getActorInformation(actorId), getActorMovies(actorId))
    }

    fun getActorInformation(id: String): ActorInfoScreen.ActorInfo {
        return actorService.getActorInfo(id)
    }

    fun getActorMovies(id: String): ActorMovies {
        return actorService.getActorMovies(id)
    }
}