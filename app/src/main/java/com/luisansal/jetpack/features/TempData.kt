package com.luisansal.jetpack.features

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.domain.entity.User

object TempData {
    var numberDNI: String = "70668281"
    var numberCE: String = "232452352323"

    var names: String = ""
    var lastnames: String = ""
    var email: String = ""
    var cellphone: String = ""

    var address : String = ""
    var addressLocation : LatLng? = null

    val user = User(id = 1, names = "Luis", lastNames = "Sánchez")
}
