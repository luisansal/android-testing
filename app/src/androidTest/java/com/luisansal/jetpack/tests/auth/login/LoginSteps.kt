package com.luisansal.jetpack.tests.auth.login

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.luisansal.jetpack.R
import com.luisansal.jetpack.features.auth.LoginActivity
import com.luisansal.jetpack.features.main.MainActivity
import com.luisansal.jetpack.tests.base.BaseIntegrationTest
import com.schibsted.spain.barista.assertion.BaristaAssertions.assertThatBackButtonClosesTheApp
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import cucumber.api.junit.Cucumber
import org.junit.Rule
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(Cucumber::class)
class LoginSteps : BaseIntegrationTest() {

    @get:Rule
    var activityRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java, true, false)

    @Before
    fun setup() {
        try {
            Intents.init()
        } catch (e: Exception) {

        }
    }

    @After
    fun cleanup() {
        setup()
        Intents.release()
    }

    @Given("The app started")
    fun the_app_started() {
        activityRule.launchActivity(Intent())
    }

    @And("I input username-email {string} and password {string} inputs box")
    fun i_fill_username_email_and_password(userNameEmail: String, password: String) {
        writeTo(R.id.etUsername, userNameEmail)
        writeTo(R.id.etPassword, password)
    }

    @When("I click on login button")
    fun i_click_on_login_button() {
        clickOn(R.id.btnLogin)
    }

    @Then("I see home login page screen")
    fun i_see_home_login_page_screen() {
        waitForView(R.id.rvFeatures) {
            intended(hasComponent(MainActivity::class.java.name))
            onView(withId(R.id.rvFeatures)).check(matches(isDisplayed()))
            //        val toolbarTitle = getInstrumentation().targetContext.getString(R.string.groceries_list_activity)
//        assertToolbarTitle(R.id.vGroceriesListToolbar, toolbarTitle)
//        assertDisplayed(R.string.groceries_list_header)
//        assertRecyclerViewItemCount(R.id.rvFeatures, expectedItemCount = 12)
        }
    }

    @And("I fill username-email {string} and password string inputs box")
    fun i_fill_username_password_incorrect(userNameEmail: String, password: String) {
        writeTo(R.id.etUsername, userNameEmail)
        writeTo(R.id.etPassword, password)
    }

    @Then("I see exception popup")
    fun i_see_popup() {
        intended(hasComponent(LoginActivity::class.java.name)) {
            //        val toolbarTitle = getInstrumentation().targetContext.getString(R.string.groceries_list_activity)
//        assertToolbarTitle(R.id.vGroceriesListToolbar, toolbarTitle)
            assertDialogText(R.string.email_or_username_incorrect)
//        assertRecyclerViewItemCount(R.id.rvFeatures, expectedItemCount = 12)
        }
    }

    @When("I press back")
    fun i_press_back() {
        // intentionally do nothing, assertion is done in the next step
    }

    @When("I press logout button")
    fun i_press_logout_button() {
        clickOn(R.id.btnLogout)
    }

    @Then("I should exit the app")
    fun i_should_exit_the_app() {
        waitForView(listOf(R.id.etUsername,R.id.etPassword)) {
            assertThatBackButtonClosesTheApp()
        }
    }
}