package com.example.appfilm.FragmentMain

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfilm.Adapter.FilmAdapterSearchResult
import com.example.appfilm.Adapter.setOnClick
import com.example.appfilm.R
import com.example.appfilm.Screen.ScreenDetailFilm
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSearch : Fragment() {
   lateinit var binding : FragmentSearchBinding
   lateinit var animation: Animation
   lateinit var viewModel: liveData
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.animation)
        viewModel = ViewModelProvider(requireActivity()).get(liveData ::class.java)
        setUpSearchAction() /// khởi tạo hành động tìm kiếm khi người dùng click
        return binding.root
    }

    private fun setUpSearchAction() {
        binding.btnSearch.setOnClickListener { // khi người dùng click button
            binding.btnSearch.startAnimation(animation)
            if(viewModel.isNetworkAvailable(requireContext()) == false ){ // kiểm tra xem có wifi hay không
                // nếu không có , hiển thị progress bar và ẩn các view khác
                binding.progressLoad.visibility = View.VISIBLE
                binding.txtInputString.visibility = View.GONE
                binding.recycleListFilmSearch.visibility = View.GONE
            }else{
                // nếu có, thực hiện chức năng tìm kiếm
                searchFilm()
            }
        }
    }

    private fun searchFilm() {
        viewModel.nameFilm.value = binding.edtInputNameFilm.text.toString()
        if( viewModel.nameFilm.value.toString().isEmpty()){
            binding.txtInputString.visibility = View.VISIBLE
            binding.recycleListFilmSearch.visibility = View.GONE
        }else{
            viewModel.getListSearchFilm(viewModel.nameFilm.value.toString())
            viewModel.listFilmSearch.observe(requireActivity()){ dataResults ->
                if( dataResults.results.isNotEmpty()){
                    binding.recycleListFilmSearch.adapter = FilmAdapterSearchResult(dataResults, object : setOnClick{
                        override fun onClickListener(pos: Int) {
                            toScreenDetail(dataResults.results[pos].id.toString(), dataResults.results[pos].title)
                        }})
                    binding.recycleListFilmSearch.layoutManager = LinearLayoutManager(requireContext())
                    binding.txtInputString.visibility = View.GONE
                    binding.recycleListFilmSearch.visibility = View.VISIBLE
                    binding.progressLoad.visibility = View.GONE
                } else{
                    binding.txtInputString.visibility = View.VISIBLE
                    binding.txtInputString.text ="Không có kết quả !"
                    binding.recycleListFilmSearch.visibility = View.GONE
                }
            }

        }

    }

    private fun toScreenDetail(id: String, name: String) { // chuyển tới màn hình chi tiết bộ phim
        val intent = Intent(requireActivity(), ScreenDetailFilm :: class.java)
        intent.putExtra("id_film",id) // chuyển id bộ phim qua màn hình chi tiết
        intent.putExtra("name_film",name) // chuyển tên bộ phim qua màn hình chi tiết
        startActivity(intent)
    }

}