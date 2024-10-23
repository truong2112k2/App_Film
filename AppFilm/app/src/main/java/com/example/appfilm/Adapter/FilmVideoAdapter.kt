package com.example.appfilm.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Model.ModelVideoFilm
import com.example.appfilm.R
import com.example.appfilm.databinding.ItemVideoFilmBinding


// adapter các video liên quan đến bộ phim
class FilmVideoAdapter(val listVideo: ModelVideoFilm, private val recyclerView: RecyclerView): RecyclerView.Adapter<FilmVideoAdapter.viewHolder>() {
    inner class viewHolder(val binding: ItemVideoFilmBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = ItemVideoFilmBinding.inflate(LayoutInflater.from(parent.context), parent,false )
        return viewHolder(view)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation)
      /// lấy ra 1 dannh sách gồm mã của 3 video liên quan đến bộ phim
        val videoIds = listVideo.items.map { it.id.videoId }
        // hiển thị từng video lên 1 webview thông qua mã của nó
        holder.binding.webview.webViewClient = WebViewClient()
        holder.binding.webview.settings.javaScriptEnabled = true
        val videoID = videoIds.get(position)
        val youtubeUrl = "https://www.youtube.com/embed/$videoID"
        holder.binding.webview.loadUrl(youtubeUrl)
        // Lắng nghe sự kiện click của nút btnBack
        holder.binding.btnBack.setOnClickListener {
            holder.binding.btnBack.startAnimation(animation)
            val currentPosition = holder.adapterPosition
            /*
            Dòng này lấy vị trí hiện tại của item mà nút btnBack thuộc về trong RecyclerView.
            adapterPosition là chỉ số của item hiện tại trong danh sách của adapter, tương ứng với vị trí của item này trong RecyclerView.
             */
            if (currentPosition > 0) { // kiểm tra xem currentPosition có lớn hơn 0 không
                // nếu lớn hơn nghĩa là nó k phải item đầu-> cho phép cuộn tiếp về item trc
                recyclerView.smoothScrollToPosition(currentPosition - 1) // Cuộn về item trước đó
            }
        }
        // Lắng nghe sự kiện click của nút btnNext
        holder.binding.btnNext.setOnClickListener {
            holder.binding.btnNext.startAnimation(animation)
            val currentPosition = holder.adapterPosition
            if (currentPosition < itemCount - 1) {
                /*
                Điều kiện này kiểm tra xem vị trí hiện tại có nhỏ hơn tổng số item trừ đi 1 hay không.
Điều này đảm bảo rằng item hiện tại không phải là item cuối cùng. Nếu item cuối cùng đang được hiển thị,
 việc cuộn về item kế tiếp sẽ không được thực hiện vì không còn item nào ở phía sau.
                 */
                recyclerView.smoothScrollToPosition(currentPosition + 1) // Cuộn đến item kế tiếp
            }
        }

    }
    override fun getItemCount(): Int {
           return listVideo.items.size

    }
}