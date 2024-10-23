package com.example.appfilm.FragmentMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appfilm.Adapter.ViewPagerAdapter
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.FragmentHomeBinding
import com.example.appfilm.databinding.ItemTablayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel : liveData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@FragmentHome).get(liveData::class.java)
        setUpViewPager()
        return binding.root
    }

    private fun setUpViewPager() { // khởi tạo adapter các thể loại phim
        binding.viewPager.adapter = ViewPagerAdapter( childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, pos ->
            when(pos){
                0 -> tab.text = "Phổ biến"
                1 -> tab.text = "Đánh giá cao"
                2 -> tab.text = "Đang chiếu"
                else -> tab.text = "Sắp chiếu"
            }
        }
            .attach()
        for( i in 0 .. 4){ // xét giao diện cho tablayout
            val tab = ItemTablayoutBinding.inflate(layoutInflater)
            binding.tabLayout.getTabAt(i)?.customView = tab.root
        }
    }


}