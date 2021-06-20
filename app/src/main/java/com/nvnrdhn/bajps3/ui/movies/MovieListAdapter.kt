package com.nvnrdhn.bajps3.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieListItem
import com.nvnrdhn.bajps3.util.OnFilmClickListener

class MovieListAdapter :
    PagingDataAdapter<MovieListItem, MovieListAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MovieListItem>() {
            override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: MovieListItem,
                newItem: MovieListItem
            ): Boolean =
                oldItem == newItem
        }
    }

    var config: ConfigurationResponse? = null
    var onFilmClickListener: OnFilmClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJudul = itemView.findViewById<TextView>(R.id.tvJudul)
        private val tvDeskripsi = itemView.findViewById<TextView>(R.id.tvDesc)
        private val tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        private val ivCover = itemView.findViewById<ImageView>(R.id.ivCover)
        fun bind(item: MovieListItem) {
            tvJudul.text = item.title
            tvDeskripsi.text = item.overview
            tvTanggal.text = item.releaseDate
            if (config != null) {
                Glide.with(itemView)
                    .load("${config!!.images.secureBaseUrl}${config!!.images.posterSizes[4]}${item.posterPath}")
                    .into(ivCover)
            }
            itemView.setOnClickListener { onFilmClickListener?.onFilmClick(item.id) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return ViewHolder(view)
    }
}