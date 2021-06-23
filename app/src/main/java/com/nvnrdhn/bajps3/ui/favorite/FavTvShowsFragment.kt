package com.nvnrdhn.bajps3.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.FragmentFavoriteListBinding
import com.nvnrdhn.bajps3.ui.adapter.FavoriteListAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.OnFilmClickListener
import dagger.hilt.android.AndroidEntryPoint

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.apply {
            adapter = tvAdapter.apply {
                onFilmClickListener = this@FavTvShowsFragment
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.rvList.isVisible = true
                binding.tvEmpty.isVisible = false
                tvAdapter.setData(it)
            }
            else {
                binding.rvList.isVisible = false
                binding.tvEmpty.isVisible = true
            }
        }
        viewModel.isLoading().observe(viewLifecycleOwner) {
            binding.pbLoading.isVisible = it
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