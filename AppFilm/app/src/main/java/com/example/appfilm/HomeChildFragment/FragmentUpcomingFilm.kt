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
import com.example.appfilm.databinding.FragmentUpcomingFilmBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUpcomingFilm.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUpcomingFilm : Fragment() {
    private val binding by lazy { FragmentUpcomingFilmBinding.inflate(layoutInflater) }
    private lateinit var viewModel: liveData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(liveData::class.java)
        getListDataFilmUpComing() // lấy danh sách các phim sắp chiếu
        setUpShimmerLayout()// set up Shimmer layout trong khi load data
        return binding.root
    }

    private fun getListDataFilmUpComing() {
        viewModel.getListFilmUpComing(1)
        viewModel.listFilmUpComing.observe(viewLifecycleOwner){ dataFilm ->
            binding.recycleFilmUpcoming.adapter = FilmAdapter(dataFilm, object: setOnClick{
                override fun onClickListener(pos: Int) {
                    toScreenDetail(dataFilm.results[pos].id.toString(), dataFilm.results[pos].title)

                }
            })
        }
        // khởi tạo recycleview
        binding.recycleFilmUpcoming.layoutManager = GridLayoutManager(requireContext(), 2)
        turnOffShimmerLayout() // tắt shimmer layout khi tải data xong
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
        binding.recycleFilmUpcoming.visibility = View.VISIBLE

    }
}