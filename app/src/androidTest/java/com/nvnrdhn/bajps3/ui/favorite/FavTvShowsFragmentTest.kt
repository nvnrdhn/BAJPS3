package com.nvnrdhn.bajps3.ui.favorite

import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.hasItem
import com.nvnrdhn.bajps3.launchFragmentInHiltContainer
import com.nvnrdhn.bajps3.ui.adapter.FavoriteListAdapter
import com.nvnrdhn.bajps3.ui.details.DetailsActivity
import com.nvnrdhn.bajps3.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
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
    }

    @Test
    fun addFavorite_check() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
            .putExtra("id", 1399)
            .putExtra("type", DetailsActivity.TYPE_TV)
        ActivityScenario.launch<DetailsActivity>(intent)
        onView(withId(R.id.action_favorite)).perform(click())
        launchFragmentInHiltContainer<FavTvShowsFragment>(themeResId = R.style.Theme_BAJPS3) {
            mockNavController.setGraph(R.navigation.mobile_navigation)
            mockNavController.setCurrentDestination(R.id.navigation_favorite)
            Navigation.setViewNavController(requireView(), mockNavController)
        }
        onView(withId(R.id.rvList))
            .perform(RecyclerViewActions.actionOnItem<FavoriteListAdapter.ViewHolder>(
                hasDescendant(withText("Game of Thrones")),
                click()))
        assertEquals(R.id.navigation_details, mockNavController.currentDestination?.id)
    }

    @Test
    fun deleteFavorite_check() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
            .putExtra("id", 1399)
            .putExtra("type", DetailsActivity.TYPE_TV)
        ActivityScenario.launch<DetailsActivity>(intent)
        onView(withId(R.id.action_favorite)).perform(click())
        launchFragmentInHiltContainer<FavMovieFragment>(themeResId = R.style.Theme_BAJPS3) {
            mockNavController.setGraph(R.navigation.mobile_navigation)
            mockNavController.setCurrentDestination(R.id.navigation_favorite)
            Navigation.setViewNavController(requireView(), mockNavController)
        }
        onView(withId(R.id.rvList))
            .check(matches(not(hasItem(hasDescendant(withText("Game of Thrones"))))))
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}