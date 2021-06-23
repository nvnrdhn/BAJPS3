package com.nvnrdhn.bajps3.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nvnrdhn.bajps3.ui.favorite.FavMovieFragment
import com.nvnrdhn.bajps3.ui.favorite.FavTvShowsFragment

class FavoritePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavMovieFragment()
            1 -> FavTvShowsFragment()
            else -> FavMovieFragment()
        }
    }
}