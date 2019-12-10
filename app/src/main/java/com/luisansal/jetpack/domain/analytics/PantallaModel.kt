package com.luisansal.jetpack.domain.analytics

import com.luisansal.jetpack.common.hardware.BuildVariant
import com.luisansal.jetpack.common.hardware.NetworkStatus
import com.luisansal.jetpack.domain.entity.Sesion

class PantallaModel(val sesion: Sesion,
                    val ambiente: BuildVariant,
                    val estadoConexion: NetworkStatus,
                    val screenName: String)