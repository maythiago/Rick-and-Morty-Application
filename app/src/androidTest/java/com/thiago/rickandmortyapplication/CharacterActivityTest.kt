package com.thiago.rickandmortyapplication

import android.content.pm.ActivityInfo
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharacterActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule<CharacterActivity>(CharacterActivity::class.java, true, false)

    @Rule
    lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start(8080)
        server.url("character")

    }

    @Test
    fun showListCharacter0() {
        val response = MockResponse()
                .addHeader("Content-Type", "application/jsonSingleCharacter; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(jsonMultipleCharacter)
        server.enqueue(response)
        activityRule.launchActivity(null)
        onView(withText("Toxic Rick1")).check(matches(isDisplayed()))
        onView(withText("Toxic Rick2")).check(matches(isDisplayed()))
        onView(withText("Ops! We do not found a character")).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenResearchCharactersCleanOlds() {
        val response = MockResponse()
                .addHeader("Content-Type", "application/jsonSingleCharacter; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(jsonMultipleCharacter)
        val emptyResponse = MockResponse()
                .addHeader("Content-Type", "application/jsonSingleCharacter; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody("{}")
                .setResponseCode(204)
        server.enqueue(response)
        server.enqueue(emptyResponse)
        var launchActivity = activityRule.launchActivity(null)
        onView(withText("Toxic Rick1")).check(matches(isDisplayed()))
        onView(withText("Toxic Rick2")).check(matches(isDisplayed()))
        launchActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withText("Ops! We do not found a character")).check(matches(isDisplayed()))
    }

    @Test
    fun whenNoCharacterFoundShows404() {
        val response = MockResponse()
                .addHeader("Content-Type", "application/jsonSingleCharacter; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setResponseCode(404)
                .setBody("{\"error\":\"There is nothing here\"}")
        server.enqueue(response)

        activityRule.launchActivity(null)
        onView(withText("Ops! We do not found a character")).check(matches(isDisplayed()))
    }

    companion object {
        val jsonMultipleCharacter = Util.loadStringFromRawResource(InstrumentationRegistry.getTargetContext().resources, R.raw.multiple_character)
    }

    @After
    fun finalize() {
        server.shutdown()
    }
}

