package com.example.appfilm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Model.ModelFilmPopular
import com.example.appfilm.databinding.ItemSearchFilmResultBinding
import com.squareup.picasso.Picasso

class FilmAdapterSearchResult(val listFilm: ModelFilmPopular,val onClick: setOnClick): RecyclerView.Adapter<FilmAdapterSearchResult.viewHolder>() {

    inner class viewHolder(val binding: ItemSearchFilmResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = ItemSearchFilmResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: FilmAdapterSearchResult.viewHolder, position: Int) {
        Picasso.get().load(getPosterFromTheFilmDb(listFilm.results[position].poster_path)).into(holder.binding.imgPoster)
        holder.binding.txtNameFilm.text = listFilm.results[position].title.toString()
        holder.binding.txtNameFilm.isSelected = true
        holder.itemView.setOnClickListener {
            onClick.onClickListener(position)
        }
    }


    override fun getItemCount(): Int {
        return listFilm.results.size
    }

    private fun getPosterFromTheFilmDb(string: String): String{
        val linkPoster = "https://image.tmdb.org/t/p/w500/${string}"
        return linkPoster
    }
}