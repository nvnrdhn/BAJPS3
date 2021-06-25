package com.nvnrdhn.bajps3.ui.favorite

import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.launchFragmentInHiltContainer
import com.nvnrdhn.bajps3.ui.adapter.FavoriteListAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavTvShowsFragmentTest {

    val mockNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
            .putExtra("id", 1399)
            .putExtra("type", DetailsActivity.TYPE_TV)
        ActivityScenario.launch<DetailsActivity>(intent)
        Espresso.onView(ViewMatchers.withId(R.id.action_favorite)).perform(ViewActions.click())
        launchFragmentInHiltContainer<FavTvShowsFragment>(themeResId = R.style.Theme_BAJPS3) {
            mockNavController.setGraph(R.navigation.mobile_navigation)
            mockNavController.setCurrentDestination(R.id.navigation_favorite)
            Navigation.setViewNavController(requireView(), mockNavController)
        }
    }

    @Test
    fun checkFavorite_click() {
        Espresso.onView(ViewMatchers.withId(R.id.rvList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<FavoriteListAdapter.FavoriteListViewHolder>(
                    0,
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