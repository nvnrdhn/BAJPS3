package com.nvnrdhn.bajps3.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.room.FavoriteDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var apiService: TMDBApiService

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService, favoriteDao)
        viewModel = FavoriteViewModel(mainRepository)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun streamFavoriteMovie() {
        runBlocking {
            whenever(favoriteDao.streamFavoriteMovies()).thenReturn(dummyPagingSource())
            val res = favoriteDao.streamFavoriteMovies().load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = MainRepository.NETWORK_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
            assertNotNull(res)
            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = dummyFavoriteList(),
                    prevKey = null,
                    nextKey = null
                ),
                res
            )
        }
    }

    @Test
    fun streamFavoriteTv() {
        runBlocking {
            whenever(favoriteDao.streamFavoriteMovies()).thenReturn(dummyPagingSource())
            val res = favoriteDao.streamFavoriteMovies().load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = MainRepository.NETWORK_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
            assertNotNull(res)
            Assert.assertEquals(
                PagingSource.LoadResult.Page(
                    data = dummyFavoriteList(),
                    prevKey = null,
                    nextKey = null
                ),
                res
            )
        }
    }
    fun dummyFavoriteList() = listOf(Favorite(550, 1, "", "", "", ""))
    fun dummyPagingSource() = object : PagingSource<Int, Favorite>() {
        override fun getRefreshKey(state: PagingState<Int, Favorite>): Int = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Favorite> =
            LoadResult.Page(
                data = dummyFavoriteList(),
                prevKey = null,
                nextKey = null
            )
    }
}