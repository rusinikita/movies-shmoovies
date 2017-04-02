package com.nikita.movies_shmoovies

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nikita.movies_shmoovies.R
import com.nikita.movies_shmoovies.common.utils.findView
import com.nikita.movies_shmoovies.posters.PostersFragment

class MoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /* TODO: Fix shadow form toolbar */
        /* TODO: Elevation may not work */
        val tabLayout = view.findView<TabLayout>(R.id.tabLayout)
        val viewPager = view.findView<ViewPager>(R.id.viewpager)
        viewPager.adapter = MoviesPagerAdapter(activity.supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    class MoviesPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        val TAB_COUNT = 3

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return PostersFragment.create(PostersFragment.Type.Popular)
                1 -> return PostersFragment.create(PostersFragment.Type.Upcoming)
                2 -> return PostersFragment.create(PostersFragment.Type.Top)
                else -> return PostersFragment.create(PostersFragment.Type.Upcoming)
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            when (position) {
                0 -> return "Popular"
                1 -> return "Upcoming"
                2 -> return "Top-10"
                else -> return "Error"
            }
        }

        override fun getCount(): Int = TAB_COUNT
    }
}