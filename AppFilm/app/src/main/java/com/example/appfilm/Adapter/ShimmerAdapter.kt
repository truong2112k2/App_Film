package com.example.appfilm.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.databinding.ItemShimmerLayoutBinding
// shimmer layout
class ShimmerAdapter(): RecyclerView.Adapter<ShimmerAdapter.theFilm>() {
    inner class theFilm(val binding: ItemShimmerLayoutBinding ): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): theFilm {
        val view = ItemShimmerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return theFilm(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: theFilm, position: Int) {


    }

    override fun getItemCount(): Int {
        return 20
    }
    
}