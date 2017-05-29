package com.nikita.movies_shmoovies.actors

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.common.mvp.BaseMvpActivity
import com.nikita.movies_shmoovies.common.mvp.LceView
import com.nikita.movies_shmoovies.common.utils.loadWithPlaceholder
import kotlinx.android.synthetic.main.activity_actor_info.*

interface ActorInfoView : LceView<ActorInfoScreen>

class ActorInfoActivity : BaseMvpActivity<ActorInfoScreen>(), ActorInfoView {

    override val layout: Int
        get() = R.layout.activity_actor_info

    @InjectPresenter
    lateinit var presenter: ActorInfoPresenter

    @ProvidePresenter
    fun providePresenter(): ActorInfoPresenter{
        var id: String
        try {
            id = intent.extras.getInt("actor_id").toString()
        } catch (e: Exception) {
            id = "10297"
        }
        return ActorInfoPresenter(appModule.actorInfoInteractor, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContent(content: ActorInfoScreen, pagination: Boolean) {
        super.setContent(content, pagination)

        actor_name.text = content.actorInfo.name
        actor_birthday.text = content.actorInfo.birthday
        actor_birthplace.text = content.actorInfo.place_of_birth
        actor_bio.text = content.actorInfo.biography

        when (content.actorInfo.gender) {
            1 -> actor_gender.text = "Female"
            2 -> actor_gender.text = "Male"
            else -> actor_gender.text = "Gender unknown"
        }

        if (content.actorInfo.profile_path != null) {
            actor_photo.loadWithPlaceholder(content.actorInfo.profile_path, R.drawable.mis_poster_placeholder)
        }
    }
}
