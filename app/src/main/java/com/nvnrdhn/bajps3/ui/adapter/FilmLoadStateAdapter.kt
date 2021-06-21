package com.nvnrdhn.bajps3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.databinding.LoadStateFooterItemBinding

class FilmLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FilmLoadStateAdapter.FilmLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FilmLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FilmLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_footer_item, parent, false)
        val binding = LoadStateFooterItemBinding.bind(view)
        return FilmLoadStateViewHolder(binding, retry)
    }

    inner class FilmLoadStateViewHolder(
        private val binding: LoadStateFooterItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }

}