package com.luisansal.jetpack.features

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.core.domain.entities.User

object TempData {
    var numberDNI: String = "70668281"
    var numberCE: String = "232452352323"

    var names: String = ""
    var lastnames: String = ""
    var email: String = ""
    var cellphone: String = ""

    var address : String = ""
    var addressDestination: String = ""
    var addressLocation : LatLng? = null
    var addressLocationDestination : LatLng? = null

    var lastLocation : String = ""

    val user = User(id = 1, names = "Luis", lastNames = "Sánchez")
}
