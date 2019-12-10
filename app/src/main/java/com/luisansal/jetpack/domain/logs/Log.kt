package com.luisansal.jetpack.domain.logs

import com.google.gson.annotations.SerializedName
import com.luisansal.jetpack.domain.logs.LogTag.ACTION
import com.luisansal.jetpack.domain.logs.LogTag.AMBIENTE
import com.luisansal.jetpack.domain.logs.LogTag.CAMPANIA
import com.luisansal.jetpack.domain.logs.LogTag.CATEGORIA
import com.luisansal.jetpack.domain.logs.LogTag.CODIGO_CONSULTORA
import com.luisansal.jetpack.domain.logs.LogTag.CUB
import com.luisansal.jetpack.domain.logs.LogTag.ESTADO_CONEXION
import com.luisansal.jetpack.domain.logs.LogTag.LABEL
import com.luisansal.jetpack.domain.logs.LogTag.PAIS_ACTUAL
import com.luisansal.jetpack.domain.logs.LogTag.PAIS_PRINCIPAL
import com.luisansal.jetpack.domain.logs.LogTag.PERIODO
import com.luisansal.jetpack.domain.logs.LogTag.REGION_ACTUAL
import com.luisansal.jetpack.domain.logs.LogTag.REGION_PRINCIPAL
import com.luisansal.jetpack.domain.logs.LogTag.ROL
import com.luisansal.jetpack.domain.logs.LogTag.SCREEN_NAME
import com.luisansal.jetpack.domain.logs.LogTag.SECCION_ACTUAL
import com.luisansal.jetpack.domain.logs.LogTag.ZONA_ACTUAL
import com.luisansal.jetpack.domain.logs.LogTag.ZONA_PRINCIPAL
import java.io.Serializable

open class Log(@SerializedName(AMBIENTE)
               val ambiente: String? = null,
               @SerializedName(SCREEN_NAME)
               val screeName: String? = null) : Serializable

class Pantalla(
               val periodo: String? = null,
               @SerializedName(CUB)
               val cub: String? = null,
               @SerializedName(ROL)
               val rol: String? = null,
               @SerializedName(ESTADO_CONEXION)
               val estadoConexion: String? = null,
               screenName: String? = null,
               ambiente: String? = null) : Log(ambiente, screenName)

class Evento(@SerializedName(CATEGORIA)
             val categoria: String? = null,
             @SerializedName(ACTION)
             val action: String? = null,
             @SerializedName(LABEL)
             val label: String? = null,
             screenName: String? = null,
             ambiente: String? = null) : Log(ambiente, screenName)