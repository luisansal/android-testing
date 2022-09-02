package com.luisansal.jetpack.tests.auth.login

import android.content.Intent
import androidx.room.util.StringUtil
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.luisansal.jetpack.R
import com.luisansal.jetpack.features.auth.LoginActivity
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
import java.util.*

@RunWith(Cucumber::class)
class NewuserSteps : BaseIntegrationTest() {

    @get:Rule
    var activityRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java, true, false)

    private var user: String = ""

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

    @Given("Se inicia la aplicacion")
    fun the_app_started() {
        activityRule.launchActivity(Intent())
    }

    @And("Me muestra la pantalla inicial LoginActivity")
    fun me_muestra_la_pantalla_inicial() {
        intended(hasComponent(LoginActivity::class.java.name))
    }

    @When("Hago click en el boton nuevo usuario")
    fun hago_click_en_el_boton_nuevo_usuario() {
        clickOn(R.id.btnNewUser)
    }

    @And("Veo formulario de nuevo usuario")
    fun veo_formulario_nuevo_usuario() {
        assertViewIsDisplayed(R.id.etEmail)
        assertViewIsDisplayed(R.id.etPassword)
        assertViewIsDisplayed(R.id.btnSave)
    }

    @And("Lleno el formulario user {string} password {string}")
    fun lleno_formulario(user: String, password: String) {
        writeTo(R.id.etEmail, user)
        writeTo(R.id.etPassword, password)
    }

    @When("Hago click en el boton guardar")
    fun hago_click_boton_guardar() {
        clickOn(R.id.btnSave)
    }

    @Then("Me muestra un snackbar con la confirmacion del usuario guardado")
    fun me_muestra_snackbar_confirmacion() {
        try {
            waitForView(listOf(R.id.etUsername, R.id.etPassword)) {
                assertSnackbarWithText(R.string.user_saved)
            }
        } catch (e: Exception) {
            clickOn(R.id.btnOk)
            lleno_formulario("${UUID.randomUUID().toString().take(5)}@gmail.com", "123456")
            hago_click_boton_guardar()
            me_muestra_snackbar_confirmacion()
        }
    }

    @And("Me regresa a la pantalla inicial")
    fun me_regresa_pantalla_inicial() {
        assertViewIsDisplayed(R.id.etUsername)
        assertViewIsDisplayed(R.id.etPassword)
        assertThatBackButtonClosesTheApp()
    }
}