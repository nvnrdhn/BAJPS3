package com.nvnrdhn.bajps3.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.FragmentTmdbListBinding
import com.nvnrdhn.bajps3.ui.adapter.FilmLoadStateAdapter
import com.nvnrdhn.bajps3.ui.adapter.MovieListAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.OnFilmClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(), OnFilmClickListener {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentTmdbListBinding? = null
    private val binding get() = _binding!!
    private val adapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTmdbListBinding.inflate(inflater, container, false)
        binding.rvList.apply {
            adapter = this@MoviesFragment.adapter.apply {
                onFilmClickListener = this@MoviesFragment
                addLoadStateListener { loadState ->
                    binding.rvList.isVisible = loadState.source.refresh is LoadState.NotLoading
                    binding.pbLoading.isVisible = loadState.source.refresh is LoadState.Loading
                    binding.btRetry.isVisible = loadState.source.refresh is LoadState.Error
                }
            }.withLoadStateFooter(FilmLoadStateAdapter { this@MoviesFragment.adapter.retry() })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        binding.btRetry.setOnClickListener { adapter.retry() }
        return binding.root
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

    override fun onFilmClick(id: Int) {
        val bundle = bundleOf(
            "id" to id,
            "type" to DetailsActivity.TYPE_MOVIE
        )
        findNavController().navigate(R.id.action_movies_to_details, bundle)
    }
}