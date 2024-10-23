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
import com.example.appfilm.Model.ModelFilmPopular
import com.example.appfilm.Screen.ScreenDetailFilm
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.FragmentPopularFilmBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPopularFilm.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPopularFilm : Fragment() {
    private val binding by lazy { FragmentPopularFilmBinding.inflate(layoutInflater)}
    private lateinit var viewModel: liveData
    private lateinit var onClick: setOnClick
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this@FragmentPopularFilm).get(liveData:: class.java)
        setUpShimmerLayout() // set up Shimmer layout trong khi load data
        getListFilmPopular() // lấy danh sách phim phổ biến
        return binding.root
    }


    private fun getListFilmPopular() {

       viewModel.getListFilmPopular(1)
        viewModel.listFilmPopular.observe(viewLifecycleOwner){ listFilm->
            setUpClickItemRecycleView(listFilm)
            binding.rclistFilmPopular.adapter = FilmAdapter(listFilm,onClick)
            binding.rclistFilmPopular.layoutManager = GridLayoutManager(requireContext(),2)
            turnOffShimmerLayout()// tắt shimmer layout khi tải data xong


        }
    }

    private fun setUpClickItemRecycleView(listFilm: ModelFilmPopular) {
         onClick = object : setOnClick{
             override fun onClickListener(pos: Int) {
                 toScreenDetail(listFilm.results[pos].id.toString(), listFilm.results[pos].title)

             }
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
        binding.rclistFilmPopular.visibility = View.VISIBLE

    }
}