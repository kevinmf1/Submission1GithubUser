package com.kevinmedia.submission1githubuser.userInterface

import androidx.appcompat.widget.SearchView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kevinmedia.submission1githubuser.UsersAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.kevinmedia.submission1githubuser.R
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.rvAdapter)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewItemClick() {
        onView(withId(R.id.rvAdapter)).perform(actionOnItemAtPosition<UsersAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.nameView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchView() {
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(typeText("test"), pressImeActionButton())
        onView(withId(R.id.rvAdapter)).check(matches(isDisplayed()))
    }

}