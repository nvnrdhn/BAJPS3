package com.nvnrdhn.bajps3.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailsActivity"
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        viewModel.fetchConfig()
        val id = intent.extras?.get("id") as Int
        when (intent.extras?.get("type") as Int) {
            TYPE_MOVIE -> {
                viewModel.getMovieDetails(id).observe(this) {
                    initView(it)
                }
            }
            TYPE_TV -> {
                viewModel.getTvDetails(id).observe(this) {
                    initView(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(details: Any?) {
        when (details) {
            is MovieDetailResponse -> {
                var genres = ""
                details.genres.forEach { genres += "${it.name}, " }
                genres = genres.dropLast(2)

                binding.tvJudul.text = details.title
                binding.tvTanggal.text = details.releaseDate
                binding.tvDuration.text = "${details.runtime}m"
                binding.tvLanguage.text = details.originalLanguage
                binding.tvTagline.text = details.tagline
                binding.tvDesc.text = details.overview
                binding.pbScore.progress = (details.voteAverage * 10).toInt()
                binding.tvScore.text = details.voteAverage.toString()
                binding.tvGenre.text = genres

                if (viewModel.config != null) {
                    Glide.with(this)
                        .load("${viewModel.config!!.images.secureBaseUrl}${viewModel.config!!.images.posterSizes[4]}${details.posterPath}")
                        .into(binding.ivCover)
                }
            }
            is TvDetailResponse -> {
                var genres = ""
                details.genres.forEach { genres += "${it.name}, " }
                genres = genres.dropLast(2)

                binding.tvJudul.text = details.name
                binding.tvTanggal.text = details.firstAirDate
                binding.tvDuration.text = "${details.episodeRunTime[0]}m x ${details.numberOfEpisodes} episodes"
                binding.tvLanguage.text = details.originalLanguage
                binding.tvTagline.text = details.tagline
                binding.tvDesc.text = details.overview
                binding.pbScore.progress = (details.voteAverage * 10).toInt()
                binding.tvScore.text = details.voteAverage.toString()
                binding.tvGenre.text = genres

                if (viewModel.config != null) {
                    Glide.with(this)
                        .load("${viewModel.config!!.images.secureBaseUrl}${viewModel.config!!.images.posterSizes[4]}${details.posterPath}")
                        .into(binding.ivCover)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}