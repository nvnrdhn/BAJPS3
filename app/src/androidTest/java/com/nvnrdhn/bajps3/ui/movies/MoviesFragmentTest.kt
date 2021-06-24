package com.nvnrdhn.bajps3.ui.movies

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.launchFragmentInHiltContainer
import com.nvnrdhn.bajps3.ui.adapter.TvListAdapter
import com.nvnrdhn.bajps3.ui.tvshows.TvShowsFragment
import com.nvnrdhn.bajps3.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MoviesFragmentTest {

    companion object {
        const val MIN_ITEM = 9
    }

    val mockNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        launchFragmentInHiltContainer<MoviesFragment>(themeResId = R.style.Theme_BAJPS3) {
            mockNavController.setGraph(R.navigation.mobile_navigation)
            mockNavController.setCurrentDestination(R.id.navigation_movies)
            Navigation.setViewNavController(requireView(), mockNavController)
        }
    }

    @Test
    fun scrollToMinItem_click() {
        Espresso.onView(ViewMatchers.withId(R.id.rvList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TvListAdapter.ViewHolder>(MIN_ITEM,
                    ViewActions.click()
                ))
        assertEquals(R.id.navigation_details, mockNavController.currentDestination?.id)
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}