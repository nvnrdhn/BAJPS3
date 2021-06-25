package com.nvnrdhn.bajps3.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.room.FavoriteDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var apiService: TMDBApiService

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    @Mock
    private lateinit var movieDetailObserver: Observer<MovieDetailResponse?>

    @Mock
    private lateinit var tvDetailObserver: Observer<TvDetailResponse?>

    @Mock
    private lateinit var favoriteObserver: Observer<Favorite?>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService, favoriteDao)
        viewModel = DetailsViewModel(mainRepository)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun fetchMovieDetails() {
        runBlocking {
            val dummy = dummyMovieDetailResponse()
            whenever(apiService.getMovieDetails(550, BuildConfig.API_KEY)).thenReturn(dummy)
            val res = viewModel.fetchMovieDetails(550)
            verify(apiService).getMovieDetails(550, BuildConfig.API_KEY)
            assertNotNull(res)
            assertNotNull(res.value)
            res.observeForever(movieDetailObserver)
            verify(movieDetailObserver).onChanged(dummy.body())
        }
    }

    @Test
    fun fetchTvDetails() {
        runBlocking {
            val dummy = dummyTvDetailResponse()
            whenever(apiService.getTvDetails(1399, BuildConfig.API_KEY)).thenReturn(dummy)
            val res = viewModel.fetchTvDetails(1399)
            verify(apiService).getTvDetails(1399, BuildConfig.API_KEY)
            assertNotNull(res)
            assertNotNull(res.value)
            res.observeForever(tvDetailObserver)
            verify(tvDetailObserver).onChanged(dummy.body())
        }
    }

    @Test
    fun fetchConfig() {
        runBlocking {
            whenever(apiService.getConfig(BuildConfig.API_KEY)).thenReturn(dummyConfigResponse())
            val res = viewModel.fetchConfig()
            verify(apiService).getConfig(BuildConfig.API_KEY)
            assertNotNull(res)
        }
    }

    @Test
    fun checkFavorite() {
        runBlocking {
            val dummy = dummyFavoriteList()
            whenever(favoriteDao.findFavoriteById(550)).thenReturn(dummy)
            val res = viewModel.checkFavorite(550)
            verify(favoriteDao).findFavoriteById(550)
            assertNotNull(res)
            assertNotNull(res.value)
            res.observeForever(favoriteObserver)
            verify(favoriteObserver).onChanged(dummy[0])
        }
    }

    @Test
    fun addFavorite() {
        runBlocking {
            val dummy = dummyFavorite()
            whenever(favoriteDao.addFavorite(dummy)).thenReturn(1)
            whenever(favoriteDao.findFavoriteById(dummy.id)).thenReturn(dummyFavoriteList())
            viewModel.addFavorite(dummy)
            verify(favoriteDao).addFavorite(dummy)
            verify(favoriteDao).findFavoriteById(dummy.id)
        }
    }

    @Test
    fun deleteFavorite() {
        runBlocking {
            val dummy = dummyFavorite()
            whenever(favoriteDao.deleteFavorite(dummy)).thenReturn(1)
            whenever(favoriteDao.findFavoriteById(dummy.id)).thenReturn(dummyFavoriteList())
            viewModel.deleteFavorite(dummy)
            verify(favoriteDao).deleteFavorite(dummy)
            verify(favoriteDao).findFavoriteById(dummy.id)
        }
    }

    fun dummyConfigResponse() = Response.success(ConfigurationResponse())
    fun dummyMovieDetailResponse() = Response.success(mock(MovieDetailResponse::class.java))
    fun dummyTvDetailResponse() = Response.success(mock(TvDetailResponse::class.java))
    fun dummyFavorite() = Favorite(550, 1, "", "", "", "")
    fun dummyFavoriteList() = listOf(dummyFavorite())
}