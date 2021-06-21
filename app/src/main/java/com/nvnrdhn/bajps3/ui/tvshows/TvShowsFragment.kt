package com.nvnrdhn.bajps3.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.FragmentTvshowsBinding
import com.nvnrdhn.bajps3.ui.adapter.FilmLoadStateAdapter
import com.nvnrdhn.bajps3.ui.adapter.TvListAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.OnFilmClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : Fragment(), OnFilmClickListener {

    private val tvShowsViewModel: TvShowsViewModel by viewModels()
    private var _binding: FragmentTvshowsBinding? = null
    private val binding get() = _binding!!
    private val adapter = TvListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvshowsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvTvshows.apply {
            adapter = this@TvShowsFragment.adapter.apply {
                onFilmClickListener = this@TvShowsFragment
            }.withLoadStateFooter(FilmLoadStateAdapter { this@TvShowsFragment.adapter.retry() })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            adapter.config = tvShowsViewModel.fetchConfig()
        }
        tvShowsViewModel.streamTvList().observe(viewLifecycleOwner) {
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
            "type" to DetailsActivity.TYPE_TV
        )
        findNavController().navigate(R.id.action_tv_to_details, bundle)
    }
}