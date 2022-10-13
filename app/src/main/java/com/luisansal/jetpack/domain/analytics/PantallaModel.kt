package com.luisansal.jetpack.domain.analytics

import com.luisansal.jetpack.utils.hardware.BuildVariant
import com.luisansal.jetpack.utils.hardware.NetworkStatus
import com.luisansal.jetpack.domain.entity.Sesion

class PantallaModel(val sesion: Sesion,
                    val ambiente: BuildVariant,
                    val estadoConexion: NetworkStatus,
                    val screenName: String)