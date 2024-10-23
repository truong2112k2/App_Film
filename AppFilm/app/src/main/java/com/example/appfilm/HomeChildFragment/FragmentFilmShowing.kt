package com.example.appfilm.HomeChildFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appfilm.Adapter.FilmAdapter
import com.example.appfilm.Adapter.ShimmerAdapter
import com.example.appfilm.Adapter.setOnClick
import com.example.appfilm.Screen.ScreenDetailFilm
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.FragmentFilmShowingBinding


class FragmentFilmShowing : Fragment() {

    private val binding by lazy { FragmentFilmShowingBinding.inflate(layoutInflater)} // khởi tạo viewbinding
    private lateinit var viewModel: liveData // khởi tạo viewmdel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(liveData::class.java)
        getListFilmPlayingNow() // lấy danh sách các bộ phim đang chiếu
        setUpShimmerLayout()// set up Shimmer layout trong khi load data

        return binding.root
    }

    private fun getListFilmPlayingNow() {
        viewModel.getListFilmPlayingNow(1) // call dữ liệu từ api
        viewModel.listFilmPlayingNow.observe(requireActivity()){ dataFilm -> // lấy dữ liệu từ viewModel và gán vào recycleview
            binding.recycleFilmPlayingNow.adapter = FilmAdapter(dataFilm, object : setOnClick{
                override fun onClickListener(pos: Int) {
                    toScreenDetail(dataFilm.results[pos].id.toString(), dataFilm.results[pos].title)
                }
            })
            //  khởi tạo recycleview
            binding.recycleFilmPlayingNow.layoutManager = GridLayoutManager(requireContext(), 2)
            turnOffShimmerLayout()// tắt shimmer layout khi tải data xong
        }
    }
    private fun toScreenDetail(id: String, name: String) { // chuyển tới màn hình chi tiết bộ phim
        val intent = Intent(requireActivity(), ScreenDetailFilm :: class.java)
        intent.putExtra("id_film",id) // chuyển id bộ phim qua màn hình chi tiết
        intent.putExtra("name_film",name) // chuyển tên bộ phim qua màn hình chi tiết
        startActivity(intent)
    }


    private fun setUpShimmerLayout() {
        binding.rcShimmerLayout.adapter = ShimmerAdapter()
        binding.rcShimmerLayout.layoutManager = GridLayoutManager(requireContext(),2)
    }
    private fun turnOffShimmerLayout(){
        binding.shimmerLayout.visibility = View.GONE
        binding.recycleFilmPlayingNow.visibility = View.VISIBLE

    }
}