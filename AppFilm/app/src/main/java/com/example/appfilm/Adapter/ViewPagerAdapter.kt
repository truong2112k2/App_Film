package com.example.appfilm.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appfilm.HomeChildFragment.FragmentFilmShowing
import com.example.appfilm.HomeChildFragment.FragmentHighestRateFilm
import com.example.appfilm.HomeChildFragment.FragmentPopularFilm
import com.example.appfilm.HomeChildFragment.FragmentUpcomingFilm
// adapter viewpager2
class ViewPagerAdapter(val fragmentManager: FragmentManager, val lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
       return 4
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0 ->{FragmentPopularFilm()}
            1 ->{FragmentHighestRateFilm()}
            2 ->{FragmentFilmShowing()}
           else -> {FragmentUpcomingFilm()}
       }
    }
}