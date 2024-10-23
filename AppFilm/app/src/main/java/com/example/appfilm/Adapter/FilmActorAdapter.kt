package com.example.appfilm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Model.ModelActorFilm
import com.example.appfilm.databinding.ItemFilmActorBinding
import com.squareup.picasso.Picasso


// adpater các recycelview các diễn viên trong 1 bộ phim
class FilmActorAdapter( val listFilmActor: ModelActorFilm, val onClick: setOnClick): RecyclerView.Adapter<FilmActorAdapter.viewHolder>() {
    inner class viewHolder(val binding: ItemFilmActorBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = ItemFilmActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        // hiển thị ảnh diễn viên
        Picasso.get().load(getPosterFromTheFilmDb(listFilmActor.cast[position].profile_path)).into(holder.binding.imgActor)
        // hiển thị tên diễn viên

        holder.binding.txtActorName.text = listFilmActor.cast[position].name
        // set on Click
        holder.itemView.setOnClickListener {
            onClick.onClickListener(position)
        }
    }

    override fun getItemCount(): Int {
       return listFilmActor.cast.size
    }

    private fun getPosterFromTheFilmDb(string: String): String{
        val linkPoster = "https://image.tmdb.org/t/p/w500/${string}"
        return linkPoster
    }
}