package com.luisansal.jetpack.features.manageusers

import com.luisansal.jetpack.ui.features.manageusers.validation.UserValidation
import org.amshove.kluent.shouldBe
import org.junit.Test

class UserValidationTest {

    @Test
    fun `Cantidad de digitos de Dni válidl`(){
        val dni = "70558281"
        UserValidation.validateDni(dni) shouldBe true
    }
}