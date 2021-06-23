package com.nvnrdhn.bajps3.ui.details

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nvnrdhn.bajps3.R
import com.nvnrdhn.bajps3.util.EspressoIdlingResource
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {


    private val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
        .putExtra("id", 550)
        .putExtra("type", 1)

    @get:Rule
    var rule = ActivityScenarioRule<DetailsActivity>(intent)

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun checkDetails() {
        onView(withId(R.id.tvJudul)).check(matches(not(withText(""))))
        onView(withId(R.id.tvTanggal)).check(matches(not(withText(""))))
        onView(withId(R.id.tvGenre)).check(matches(not(withText(""))))
        onView(withId(R.id.tvScore)).check(matches(not(withText(""))))
        onView(withId(R.id.tvDesc)).check(matches(not(withText(""))))
        onView(withId(R.id.tvDuration)).check(matches(not(withText(""))))
        onView(withId(R.id.tvScore)).check(matches(not(withText(""))))
        onView(withId(R.id.tvLanguage)).check(matches(not(withText(""))))
        onView(withId(R.id.tvTagline)).check(matches(not(withText(""))))
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}