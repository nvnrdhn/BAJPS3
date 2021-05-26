package com.nvnrdhn.bajps3.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nvnrdhn.bajps3.databinding.FragmentTvshowsBinding

class TvShowsFragment : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private var _binding: FragmentTvshowsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowsViewModel =
            ViewModelProvider(this).get(TvShowsViewModel::class.java)

        _binding = FragmentTvshowsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        tvShowsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}