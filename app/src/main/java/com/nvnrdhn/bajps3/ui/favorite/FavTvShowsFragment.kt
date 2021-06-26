package com.nvnrdhn.bajps3.ui.favorite

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
import com.nvnrdhn.bajps3.databinding.FragmentFavoriteListBinding
import com.nvnrdhn.bajps3.ui.adapter.FavoriteListAdapter
import com.nvnrdhn.bajps3.ui.adapter.FilmLoadStateAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.OnFilmClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavTvShowsFragment : Fragment(), OnFilmClickListener {

    private val viewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!
    private val tvAdapter = FavoriteListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)
        binding.rvList.apply {
            adapter = tvAdapter.apply {
                onFilmClickListener = this@FavTvShowsFragment
                addLoadStateListener { loadState ->
                    binding.rvList.isVisible = loadState.source.refresh is LoadState.NotLoading
                    binding.pbLoading.isVisible = loadState.source.refresh is LoadState.Loading
                }
            }.withLoadStateFooter(FilmLoadStateAdapter { tvAdapter.retry() })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.streamFavoriteTv().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                tvAdapter.submitData(it)
            }
        }
    }

    override fun onFilmClick(id: Int) {
        val bundle = bundleOf(
            "id" to id,
            "type" to DetailsActivity.TYPE_TV
        )
        findNavController().navigate(R.id.action_favorite_to_details, bundle)
    }

}