package com.nikita.movies_shmoovies.actors

import com.nikita.movies_shmoovies.BuildConfig
import com.nikita.movies_shmoovies.common.network.BaseService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorApi {

    @GET("3/person/{actor_id}")
    fun getActorInfo(@Path("actor_id") id: String,
                     @Query("api_key") apiKey: String): Call<ActorInfoScreen.ActorInfo>

    @GET("3/person/{actor_id}/movie_credits")
    fun getActorMovies(@Path("actor_id") id: String,
                     @Query("api_key") apiKey: String): Call<ActorInfoScreen.ActorInfo.ActorMovies>
}

class ActorInfoService(api: ActorApi) : BaseService<ActorApi>(api) {
    fun getActorInfo(id: String) = api.getActorInfo(id, BuildConfig.API_KEY).executeUnsafe()
    fun getActorMovies(id: String) = api.getActorMovies(id, BuildConfig.API_KEY).executeUnsafe()
}
