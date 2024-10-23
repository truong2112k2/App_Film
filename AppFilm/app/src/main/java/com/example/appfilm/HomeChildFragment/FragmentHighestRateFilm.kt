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
import com.example.appfilm.databinding.FragmentHighestRateFilmBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHighestRateFilm.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHighestRateFilm : Fragment() {
  private val binding by lazy { FragmentHighestRateFilmBinding.inflate(layoutInflater) }
  private lateinit var viewModel: liveData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this@FragmentHighestRateFilm).get(liveData::class.java)
        getListFilmHighestRate() // lấy danh sách phim theo đánh giá cao
        setUpShimmerLayout()// set up Shimmer layout trong khi load data
        return binding.root
    }

    private fun getListFilmHighestRate() {
        viewModel.getListHighestRateFilm(1)
        viewModel.listFilmHighestRate.observe(requireActivity()){data->
            binding.rvHighestRateFilm.adapter = FilmAdapter(data, object : setOnClick{
                override fun onClickListener(pos: Int) {
                    toScreenDetail(data.results[pos].id.toString(), data.results[pos].title)

                } })
            binding.rvHighestRateFilm.layoutManager = GridLayoutManager(requireContext(), 2)
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
        binding.rvHighestRateFilm.visibility = View.VISIBLE

    }
}