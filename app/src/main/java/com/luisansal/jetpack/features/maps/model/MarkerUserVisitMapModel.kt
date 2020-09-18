package com.luisansal.jetpack.features.maps.model

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.entity.Visit

class MarkerUserVisitMapModel (val visits: List<Visit>,
                               val user : User)