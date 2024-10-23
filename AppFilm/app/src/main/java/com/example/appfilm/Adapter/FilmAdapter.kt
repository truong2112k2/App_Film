package com.example.appfilm.Adapter

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Model.ModelFilmPopular
import com.example.appfilm.databinding.ItemPopularfilmBinding
import com.squareup.picasso.Picasso

/// adapter reycleview các bộ phim
class FilmAdapter(val dataFilm: ModelFilmPopular, val setOnClick: setOnClick): RecyclerView.Adapter<FilmAdapter.theFilm>() {
    inner class theFilm(val binding: ItemPopularfilmBinding ): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): theFilm {
        val view = ItemPopularfilmBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return theFilm(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: theFilm, position: Int) {
        holder.binding.txtTitleFilm.isSelected = true // cho tên phim chuyển động nếu nó dài
       // hiển thị poster phim
        Picasso.get().load(getPosterFromTheFilmDb(dataFilm.results[position].poster_path)).into(holder.binding.posterFilm)
       // hiển thị số điểm đánh giá
        holder.binding.txtVoteAvarage.text = forMatNumber(dataFilm.results[position].vote_average) +"/10"
       // hiển thị tên phim
        holder.binding.txtTitleFilm.text = dataFilm.results[position].title
        // xét click item
        holder.itemView.setOnClickListener {
            setOnClick.onClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return dataFilm.results.size
    }

    private fun getPosterFromTheFilmDb(string: String): String{
        val linkPoster = "https://image.tmdb.org/t/p/w500/${string}"
        return linkPoster
    }

    private fun forMatNumber(string: Double): String{
        val decimalFormat = DecimalFormat("#.0")
        return decimalFormat.format(string)
    }
}