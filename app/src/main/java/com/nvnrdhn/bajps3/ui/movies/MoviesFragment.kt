package com.nvnrdhn.bajps3.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieListItem
import com.nvnrdhn.bajps3.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvMovies.apply {
            adapter = this@MoviesFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            adapter.config = moviesViewModel.fetchConfig()
        }
        moviesViewModel.streamMovieList().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private class MovieListAdapter :
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