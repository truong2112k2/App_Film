package com.example.appfilm.Screen

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.appfilm.Adapter.FilmActorAdapter
import com.example.appfilm.Adapter.FilmVideoAdapter
import com.example.appfilm.Adapter.setOnClick
import com.example.appfilm.Model.ModelActorFilm
//import com.example.appfilm.Adapter.FilmVideoAdapter
import com.example.appfilm.Model.ModelFilmDetail
import com.example.appfilm.R
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityScreenDetailFilmBinding
import com.example.appfilm.databinding.AlertDialogActorDetailBinding
import com.squareup.picasso.Picasso

class ScreenDetailFilm : AppCompatActivity() {
    private val binding by lazy { ActivityScreenDetailFilmBinding.inflate(layoutInflater) }
    private val viewModel: liveData by viewModels()
    val snapHelper = PagerSnapHelper() // cài đặt lướt 1 lần item recycleview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (viewModel.isNetworkAvailable(this@ScreenDetailFilm) == false) {
            viewModel.createASnackBar(
                "Kiểm tra kết nối mạng !",
                this@ScreenDetailFilm,
                binding.linearAllView
            )
        } else {
            getDetailFilmFunction()
            getListActorFilmFunction()
            getListVideoFilmFunction()

        }


    }


    private fun getListVideoFilmFunction() { // danh sách các video liên quan đến bộ phim
        val name = intent.getStringExtra("name_film")
        viewModel.getListVideoFilm(name + "Trailer Official")
        viewModel.listVideoFilm.observe(this@ScreenDetailFilm) { listVideo ->
            binding.linearAllView.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
            if (listVideo.items.isNotEmpty()) {
                binding.recycleVideo.visibility = View.VISIBLE
                binding.recycleVideo.adapter = FilmVideoAdapter(listVideo, binding.recycleVideo)
                binding.recycleVideo.layoutManager = LinearLayoutManager(
                    this@ScreenDetailFilm,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                snapHelper.attachToRecyclerView(binding.recycleVideo)
            } else {
                binding.recycleVideo.visibility = View.GONE
                binding.txtNoVideo.visibility = View.VISIBLE
            }
        }
    }

    private fun getListActorFilmFunction() { // danh sách các diễn viên chính của bộ phim
        viewModel.idFilm.value = intent.getStringExtra("id_film")
        viewModel.getListActorFilm(viewModel.idFilm.value.toString().toInt())
        viewModel.listActorFilm.observe(this@ScreenDetailFilm) { listActor ->
            if (listActor.cast.isEmpty()) {
                binding.txtNoListActor.visibility = View.VISIBLE
                binding.recycleActor.visibility = View.GONE
            } else {
                binding.txtNoListActor.visibility = View.GONE
                binding.recycleActor.visibility = View.VISIBLE
                binding.recycleActor.adapter = FilmActorAdapter(listActor, object : setOnClick {
                    override fun onClickListener(pos: Int) {
                        createAlertDialogDetailActor(listActor, pos) // hiển thị thông tin chi tiết diễn viên phim
                    }
                })
                binding.recycleActor.layoutManager = LinearLayoutManager(
                    this@ScreenDetailFilm,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }


        }
    }

    @SuppressLint("SetTextI18n")
    private fun createAlertDialogDetailActor(listActor: ModelActorFilm?, pos: Int) {
        val build = AlertDialog.Builder(this@ScreenDetailFilm)
        val view = AlertDialogActorDetailBinding.inflate(layoutInflater)
        build.setView(view.root)
        // đưa thông tin diễn viên lên alertdialog
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${listActor!!.cast[pos].profile_path}").into(view.imgImageActor)
        view.txtActorName.text = listActor.cast[pos].name
        view.txtActorCharacter.text = "Vai diễn: " + listActor.cast[pos].character
        view.txtActorGender.text = "Giới tính: " + viewModel.forMatGender(listActor.cast[pos].gender)
        val dialogDetailActor = build.create()
        dialogDetailActor.show()

        dialogDetailActor.window?.setBackgroundDrawableResource(R.drawable.background_alertdialog) // xét background cho alerdialog(khi alerdialog là cardview)
        dialogDetailActor.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun getDetailFilmFunction() { // lấy thông tin chi tiết phim
        viewModel.idFilm.value = intent.getStringExtra("id_film")
        viewModel.getDetailFilmWithID(viewModel.idFilm.value.toString().toInt())
        viewModel.detailFilm.observe(this@ScreenDetailFilm) { dataDetailFim ->
            showDetailFilmFunction(dataDetailFim) // hiển thị thông tin chi tiết phim lên giao diện
            loveFilmFuntion(dataDetailFim) // thêm phim được yêu thích
        }
    }

    private fun loveFilmFuntion(dataDetailFim: ModelFilmDetail) {
        binding.checkLoveFilm.setOnCheckedChangeListener { compoundButton, isChecked -> // nếu checkboxx đc chọn
            if (isChecked) {
                // lấy id của tài khooản viewModel.getIdUser()
                createAlertDialogLoveFilm(dataDetailFim) // hàm thực hiện thêm phim được yêu thích vào firebase
            }
        }
    }

    private fun createAlertDialogLoveFilm(dataDetail: ModelFilmDetail) {
        val alertDialog = AlertDialog.Builder(this@ScreenDetailFilm)
       .setTitle("Yêu thích phim ♥")
            .setMessage("Thêm phim vào mục yêu thích?")
            .setPositiveButton("[Đồng ý]"){ dialogInterface: DialogInterface, i: Int ->
               viewModel.addLoveFilm(viewModel.getIdUser(), dataDetail)
                viewModel.addLoveFilmTitle.observe(this@ScreenDetailFilm){
                    viewModel.createASnackBar("Thêm thành công !", this@ScreenDetailFilm, binding.checkLoveFilm)
                    binding.checkLoveFilm.isChecked = false
                }
            }
            .setNegativeButton("[Hủy Bỏ]"){ dialogInterface: DialogInterface, i: Int ->
                viewModel.createASnackBar("Hủy thành công !", this@ScreenDetailFilm, binding.checkLoveFilm)

                binding.checkLoveFilm.isChecked = false
            }
          alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailFilmFunction(dataDetailFim: ModelFilmDetail) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${dataDetailFim.poster_path}")
            .into(binding.imgPoster)
        binding.txtTitle.text = dataDetailFim.title
        binding.txtGenre.isSelected = true
        for (i in dataDetailFim.genres) {
            viewModel.genreFilm.value += i.name + " "
            binding.txtGenre.text = viewModel.genreFilm.value.toString()
        } // danh sách thể loại phim

        for (i in dataDetailFim.origin_country) {
            if (i.length > 2) {
                binding.txtCountry.isSelected = true
            } else {
                binding.txtCountry.isSelected = false
            }
            viewModel.countryFilm.value += i + " "
            binding.txtCountry.text = viewModel.countryFilm.value.toString()
        } // danh sách các nước sản xuất phim

        binding.txtDate.text =
            viewModel.reverseDateString(dataDetailFim.release_date.toString()) // ngaày ra mắt
        if (dataDetailFim.overview.length == 0) {
            binding.txtOverview.text = "Nội dung chưa cập nhật !"
        } else {
            binding.txtOverview.text = dataDetailFim.overview.toString()
        }// nội dung chính phim
        binding.txtVoteAvarage.text =
            viewModel.forMatNumber(dataDetailFim.vote_average) + " / 10" // điểm đánh giá
        binding.txtVoteCount.text =
            dataDetailFim.vote_count.toString() + " lượt đánh giá"// số lần đánh giá
        if (dataDetailFim.budget == 0) { // hiển thị chi phí
            binding.txtbudget.text = "Chi phí: Chưa rõ "
        } else {
            binding.txtbudget.text =
                "Chi phí: " + viewModel.formatCurrencyUSD(dataDetailFim.budget.toDouble())
        }
        for (i in dataDetailFim.spoken_languages) {
            viewModel.spokenLanguage.value += i.name
            binding.txtSpokenLanguage.text = viewModel.spokenLanguage.value.toString()
        } // danh sách các ngôn ngữ trong phim

        if (dataDetailFim.revenue == 0.toLong()) { // hiển thị doanh thu
            binding.txtRevenue.text = "Doanh thu: Chưa rõ"

        } else {
            binding.txtRevenue.text =
                "Doanh thu: " + viewModel.formatCurrencyUSD(dataDetailFim.revenue.toDouble()) // doanh thu

        }
        binding.txtRuntime.text =
            "Thời lượng: " + dataDetailFim.runtime.toString() + " phút" // thời lượng


    }
}