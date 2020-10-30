package com.luisansal.jetpack.domain.entity

import com.luisansal.jetpack.domain.entity.User
import java.util.Date

abstract class Auditory {

    var createdBy: User? = null

    var modifiedBy: User? = null

    var dateCreated = Date()

    var dateModified: Date? = null

    var status: Boolean? = true
}
