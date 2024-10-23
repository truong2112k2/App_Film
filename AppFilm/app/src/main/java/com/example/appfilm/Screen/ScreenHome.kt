package com.example.appfilm.Screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appfilm.FragmentMain.FragmentHome
import com.example.appfilm.FragmentMain.FragmentLove
import com.example.appfilm.FragmentMain.FragmentProfile
import com.example.appfilm.FragmentMain.FragmentSearch
import com.example.appfilm.R
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityScreenHomeBinding

class ScreenHome : AppCompatActivity() {
    private val binding by lazy { ActivityScreenHomeBinding.inflate(layoutInflater) }
    private val viewModel: liveData by viewModels()
    private var id : String = ""
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)







       if( viewModel.isNetworkAvailable(this@ScreenHome) == false ){ // nếu k có kết nối mạng
           viewModel.createASnackBar("Kiểm tra kết nối mạng", this@ScreenHome, binding.progressLoad) // thông báo đến ng dùng
           binding.progressLoad.visibility = View.VISIBLE // cho progress loading hiện lên
       }else{ // nếu có mạng
           setUpNavitionMenu() // hiển thị menu
           binding.progressLoad.visibility = View.GONE // cho progress loading ẩn
           binding.main.visibility = View.VISIBLE // cho recycleview phim hiện
           //// cài đặt cho tab đầu tiên được chọn trong navition bottom menu là tab home
           if( savedInstanceState == null ){
               toFragmentHome()
               binding.navitionMenu.selectedItemId = R.id.tab_home
           }
       }
    }
    private fun setUpNavitionMenu() { // set up navtition menu
        binding.navitionMenu.itemIconTintList = null
        binding.navitionMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    changeIconTabHome()
                    toFragmentHome()
                }

                R.id.tab_love -> {
                    changeIconTabLove()
                    toFragmentLove()
                }
                R.id.tab_search -> {
                    changeIconTabSearch()
                    toFragmentSearch()
                }
            }
            true
        }
    }


    /*****************************************************************/


    private fun toFragmentSearch() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, FragmentSearch())
            commit()
        }
    }

    private fun toFragmentProfile() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, FragmentProfile())
            commit()
        }
    }

    private fun toFragmentLove() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, FragmentLove())
            commit()
        }
    }

    private fun toFragmentHome() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, FragmentHome())

            bundle.putString("id_user", id)
            FragmentHome().arguments = bundle

            commit()
        }
    }

    private fun changeIconTabSearch() {
        binding.navitionMenu.menu.findItem(R.id.tab_home).setIcon(R.drawable.ic_home_default)
        binding.navitionMenu.menu.findItem(R.id.tab_love).setIcon(R.drawable.ic_love_default)
        binding.navitionMenu.menu.findItem(R.id.tab_search).setIcon(R.drawable.ic_search_select)
    }



    private fun changeIconTabLove() {
        binding.navitionMenu.menu.findItem(R.id.tab_home).setIcon(R.drawable.ic_home_default)
        binding.navitionMenu.menu.findItem(R.id.tab_love).setIcon(R.drawable.ic_love_select)
        binding.navitionMenu.menu.findItem(R.id.tab_search).setIcon(R.drawable.ic_search_default)
    }

    private fun changeIconTabHome() {
        binding.navitionMenu.menu.findItem(R.id.tab_home).setIcon(R.drawable.ic_home_select)
        binding.navitionMenu.menu.findItem(R.id.tab_love).setIcon(R.drawable.ic_love_default)

        binding.navitionMenu.menu.findItem(R.id.tab_search).setIcon(R.drawable.ic_search_default)
    }
}