package com.nvnrdhn.bajps3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.FilmItemBinding
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.util.OnFilmClickListener

class FavoriteListAdapter :
    PagingDataAdapter<Favorite, FavoriteListAdapter.ViewHolder>(REPO_COMPARATOR) {
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: Favorite,
                newItem: Favorite
            ): Boolean =
                oldItem == newItem
        }
    }

    var onFilmClickListener: OnFilmClickListener? = null

    inner class ViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        val binding = FilmItemBinding.bind(view)
        return ViewHolder(binding)
    }

}