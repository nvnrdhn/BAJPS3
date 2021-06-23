package com.nvnrdhn.bajps3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.FilmItemBinding
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.util.OnFilmClickListener

class FavoriteListAdapter() : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>() {

    var onFilmClickListener: OnFilmClickListener? = null
    private val list = arrayListOf<Favorite>()

    inner class FavoriteListViewHolder(private val binding: FilmItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) {
            binding.tvJudul.text = item.title
            binding.tvDesc.text = item.description
            binding.tvTanggal.text = item.releaseDate
            Glide.with(itemView)
                .load(item.coverUrl)
                .into(binding.ivCover)
            binding.root.setOnClickListener { onFilmClickListener?.onFilmClick(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        val binding = FilmItemBinding.bind(view)
        return FavoriteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setData(data: List<Favorite>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}