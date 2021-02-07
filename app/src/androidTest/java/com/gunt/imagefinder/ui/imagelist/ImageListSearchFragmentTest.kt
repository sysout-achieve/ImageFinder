package com.gunt.imagefinder.ui.imagelist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gunt.imagefinder.R
import com.gunt.imagefinder.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ImageListSearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        launchFragmentInHiltContainer<ImageListSearchFragment>()
    }

    @Test
    fun fragmentInViewTest() {
        onView(withId(R.id.edit_search)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_category)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_category)).check(matches(withText("ALL")))
        onView(withId(R.id.image_list)).check(matches(isDisplayed()))
    }

    @Test
    fun editTextChangeTest() {
        // given
        onView(withId(R.id.edit_search)).perform(click())

        // when
        onView(withId(R.id.edit_search)).perform(typeTextIntoFocusedView("hi"))

        // then
        onView(withId(R.id.edit_search)).check(matches(withText("hi")))
    }

    @Test
    fun clickButtonCategoryTest() {
        // given
        onView(withId(R.id.btn_category)).perform(click())

        // then
        onView(withText(R.string.select_category)).check(matches(isDisplayed()))
    }
}
