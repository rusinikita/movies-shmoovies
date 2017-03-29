package com.nikita.movies_shmoovies.posters

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.appModule
import com.nikita.movies_shmoovies.common.EXTRA_TYPE
import com.nikita.movies_shmoovies.common.mvp.BaseMvpFragment
import com.nikita.movies_shmoovies.common.mvp.ErrorDesc
import com.nikita.movies_shmoovies.common.utils.findView

class PostersFragment : BaseMvpFragment<PostersPM>(), PostersView {
  companion object {
    fun create(type: Type) : PostersFragment {
      val fragment = PostersFragment()
      fragment.arguments = Bundle(1).apply { putString(EXTRA_TYPE, type.name) }
      return fragment
    }
  }
  override val layout: Int = R.layout.posters_content
  lateinit var swipeLayout : SwipeRefreshLayout

  @InjectPresenter
  lateinit var presenter: PostersPresenter

  @ProvidePresenter
  fun providePresenter(): PostersPresenter {
    val type = Type.valueOf(arguments.getString(EXTRA_TYPE))
    val appModule = activity.appModule
    val behavior = when (type) {
      Type.Popular -> PopularPostersBehavior(appModule.postersInteractor, appModule.appRouter)
      Type.Upcoming -> UpcomingPostersBehavior(appModule.postersInteractor, appModule.appRouter)
      Type.Top -> TopPostersBehavior(appModule.postersInteractor, appModule.appRouter)
      Type.TvShows -> TvPostersBehavior(appModule.postersInteractor, appModule.appRouter)
      Type.People -> PeoplePostersBehavior(appModule.postersInteractor, appModule.appRouter)
    }
    return PostersPresenter(behavior)
  }

  private val adapter = PostersAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val recyclerView = view.findView<RecyclerView>(R.id.content_view)
    swipeLayout = view.findView<SwipeRefreshLayout>(R.id.swipeLayout)
    recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    recyclerView.adapter = adapter
    adapter.itemClickAction = { presenter.onPosterClick(it.id) }
    swipeLayout.setOnRefreshListener { presenter.onRefreshTriggered() }
  }

  override fun setContent(content: PostersPM) {
    super.setContent(content)
    adapter.data = content.posters
    swipeLayout.isRefreshing = false
  }

  /* TODO: Investigate where is the progress bar end error message? */

  override fun switchToLoading(pullToRefresh: Boolean) {
    super.switchToLoading(pullToRefresh)
  }

  override fun switchToError(errorDesc: ErrorDesc) {
    super.switchToError(errorDesc)
    swipeLayout.isRefreshing = false
  }

  enum class Type {
    Upcoming, TvShows, People, Popular, Top
  }
}