package com.nvnrdhn.bajps3.ui.tvshows

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.launchFragmentInHiltContainer
import com.nvnrdhn.bajps3.ui.adapter.TvListAdapter
import com.nvnrdhn.bajps3.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class TvShowsFragmentTest {

    companion object {
        const val MIN_ITEM = 9
    }

    val mockNavController = mock(NavController::class.java)

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        launchFragmentInHiltContainer<TvShowsFragment>(themeResId = R.style.Theme_BAJPS3) {
            // In addition to returning a new instance of our Fragment,
            // get a callback whenever the fragment’s view is created
            // or destroyed so that we can set the mock NavController
            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // The fragment’s view has just been created
                    Navigation.setViewNavController(requireView(), mockNavController)
                }
            }
        }
    }

    @Test
    fun scrollToMinItem_click() {
        onView(ViewMatchers.withId(R.id.rvList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TvListAdapter.ViewHolder>(MIN_ITEM,
                    ViewActions.click()
                ))
        // TODO: masih salah
        assertEquals(R.id.navigation_details, mockNavController.currentDestination?.id)
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}