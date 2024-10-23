package com.example.appfilm.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Model.ModelFilmDetail
import com.example.appfilm.Model.ModelLoveFilm

import com.example.appfilm.databinding.ItemLoveFilmBinding
import com.squareup.picasso.Picasso

class FilmLoveAdapter(val list: List<ModelLoveFilm>, val setOnClick: setOnClick): RecyclerView.Adapter<FilmLoveAdapter.viewHolder>() {
    inner class viewHolder(val binding: ItemLoveFilmBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = ItemLoveFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       Picasso.get().load(getPosterFromTheFilmDb(list[position].film!!.poster_path)).into(holder.binding.imgPoster)
        holder.binding.txtNameFilm.text = list[position].film!!.title
        holder.binding.txtNameFilm.isSelected = true
        holder.itemView.setOnClickListener {
            setOnClick.onClickListener(position)
        }

    }

    override fun getItemCount(): Int {
         return list.size
    }


    private fun getPosterFromTheFilmDb(string: String): String{
        val linkPoster = "https://image.tmdb.org/t/p/w500/${string}"
        return linkPoster
    }

}