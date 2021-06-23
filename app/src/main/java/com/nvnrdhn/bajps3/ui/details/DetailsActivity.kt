package com.nvnrdhn.bajps3.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.databinding.ActivityDetailsBinding
import com.nvnrdhn.bajps3.room.Favorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()
    private var isFavorite: Favorite? = null
    private var data: Any? = null

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
        viewModel.isLoading().observe(this) {
            binding.scrollView.isVisible = !it
            binding.pbLoading.isVisible = it
            binding.btRetry.isVisible = false
        }
        binding.btRetry.setOnClickListener { loadData() }
        loadData()
    }

    private fun checkFavorite(id: Int) {
        viewModel.checkFavorite(id).observe(this) {
            isFavorite = it
            invalidateOptionsMenu()
        }
    }

    private fun loadData() {
        val id = intent.extras?.get("id") as Int
        when (intent.extras?.get("type") as Int) {
            TYPE_MOVIE -> {
                viewModel.fetchMovieDetails(id).observe(this) {
                    if (it != null) {
                        initView(it)
                        checkFavorite(it.id)
                    }
                    else {
                        binding.scrollView.isVisible = false
                        binding.btRetry.isVisible = true
                    }
                }
            }
            TYPE_TV -> {
                viewModel.fetchTvDetails(id).observe(this) {
                    if (it != null) {
                        initView(it)
                        checkFavorite(it.id)
                    }
                    else {
                        binding.scrollView.isVisible = false
                        binding.btRetry.isVisible = true
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(details: Any?) {
        binding.scrollView.isVisible = true
        binding.btRetry.isVisible = false
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
        menu?.findItem(R.id.action_favorite)?.icon =
            if (isFavorite != null) ResourcesCompat.getDrawable(resources, R.drawable.outline_favorite_24, theme)
            else ResourcesCompat.getDrawable(resources, R.drawable.outline_favorite_border_24, theme)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_favorite -> {
                Log.d("action_favorite", "clicked!. fav = $isFavorite")
                if (isFavorite != null) viewModel.deleteFavorite(isFavorite)
                else {
                    when (intent.extras?.get("type") as Int) {
                        TYPE_MOVIE -> {
                            val details = viewModel.getMovieDetails()
                            if (details != null) {
                                val fav = Favorite(
                                    details.id,
                                    TYPE_MOVIE,
                                    "${viewModel.config!!.images.secureBaseUrl}${viewModel.config!!.images.posterSizes[4]}${details.posterPath}",
                                    details.title,
                                    details.releaseDate,
                                    details.overview
                                )
                                viewModel.addFavorite(fav)
                            }
                        }
                        TYPE_TV -> {
                            val details = viewModel.getTvDetails()
                            if (details != null) {
                                val fav = Favorite(
                                    details.id,
                                    TYPE_TV,
                                    "${viewModel.config!!.images.secureBaseUrl}${viewModel.config!!.images.posterSizes[4]}${details.posterPath}",
                                    details.name,
                                    details.firstAirDate,
                                    details.overview
                                )
                                viewModel.addFavorite(fav)
                            }
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}