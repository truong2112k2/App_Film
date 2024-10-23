package com.example.appfilm.FragmentMain

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfilm.Adapter.FilmLoveAdapter
import com.example.appfilm.Adapter.setOnClick
import com.example.appfilm.Model.ModelFilmDetail
import com.example.appfilm.Model.ModelLoveFilm
import com.example.appfilm.Screen.ScreenDetailFilm
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.FragmentLoveBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLove.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLove : Fragment() {
  lateinit var binding: FragmentLoveBinding
  lateinit var viewModels: liveData
  lateinit var listLoveFilm: ArrayList<ModelLoveFilm>
  lateinit var adapter : FilmLoveAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoveBinding.inflate(inflater, container, false)
        viewModels = ViewModelProvider(requireActivity()).get(liveData::class.java)
        listLoveFilm = ArrayList<ModelLoveFilm>()
        getListFilmLove() // hàm lấy list phim đã lưu
        return binding.root

    }
    private fun getListFilmLove() {
        // Lấy reference từ Firebase
        viewModels.firebaseDatabase.getReference(viewModels.getIdUser()).addChildEventListener(object :
            ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // Khi có một node con mới được thêm vào
                val data = snapshot.getValue(ModelLoveFilm::class.java)
                if (data != null) {
                    listLoveFilm.add(data)
                    binding.progressLoad.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    // Cập nhật lại RecyclerView
                    binding.recycleLoveFilm.adapter?.notifyItemInserted(listLoveFilm.size - 1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Khi một node con bị thay đổi
                val data = snapshot.getValue(ModelLoveFilm::class.java)
                if (data != null) {
                    val index = listLoveFilm.indexOfFirst { it.id_db == data.id_db  }
                    if (index != -1) {
                        listLoveFilm[index] = data
                        // Cập nhật lại RecyclerView
                        binding.recycleLoveFilm.adapter?.notifyItemChanged(index)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Khi một node con bị xóa
                val data = snapshot.getValue(ModelLoveFilm::class.java)
                if (data != null) {
                    val index = listLoveFilm.indexOfFirst { it.id_db == data.id_db }
                    if (index != -1) {
                        listLoveFilm.removeAt(index)
                        // Cập nhật lại RecyclerView
                        binding.recycleLoveFilm.adapter?.notifyItemRemoved(index)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Khi một node con được di chuyển vị trí trong danh sách
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressLoad.visibility = View.VISIBLE
            }
        })

        // Thiết lập adapter và layout manager cho RecyclerView
        binding.recycleLoveFilm.adapter = FilmLoveAdapter(listLoveFilm, object : setOnClick {
            override fun onClickListener(pos: Int) {
                createDiaLog(listLoveFilm[pos])
            }
        })
        binding.recycleLoveFilm.layoutManager = LinearLayoutManager(requireContext())
    }



    private fun createDiaLog(modelFilmDetail: ModelLoveFilm) { // alert dialog xóa hoặc xem chi tiết phim đã lưu
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(modelFilmDetail.film!!.title)
            .setPositiveButton("[Chi Tiết]"){ dialogInterface: DialogInterface, i: Int ->
               toScreenDetail(modelFilmDetail.film.id.toString(), modelFilmDetail.film.title)
            }
            .setNegativeButton("[Xóa]"){ dialogInterface: DialogInterface, i: Int ->
               deleteFilmLove(modelFilmDetail.id_db.toString())
            }
           dialog.show()
    }

    private fun deleteFilmLove(id: String) { // thực hiện xóa phim nếu được chọn
      viewModels.firebaseDatabase.getReference(viewModels.getIdUser().toString()).child(id.toString())
          .removeValue()
          .addOnCompleteListener {
              if(it.isSuccessful){
                  viewModels.createASnackBar("Xóa thành công !", requireContext(), binding.recycleLoveFilm)

              }else{
                  viewModels.createASnackBar("Xóa thất bại !", requireContext(), binding.recycleLoveFilm)

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