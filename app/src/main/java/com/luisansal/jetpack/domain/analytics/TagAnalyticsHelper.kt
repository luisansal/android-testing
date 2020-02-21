package com.luisansal.jetpack.domain.analytics

import com.luisansal.jetpack.domain.analytics.TagAnalytics.*
import com.luisansal.jetpack.domain.exception.AnalyticsEventException
import com.luisansal.jetpack.domain.entity.Sesion
import com.luisansal.jetpack.domain.model.user.Rol

object TagAnalyticsHelper {
    const val RDD = "Rutas de desarrollo"
    private const val MI_RUTA = "Mi ruta de desarrollo"
    private const val LISTADO = "Lista"
    private const val ACOMPANIAMIENTO = "Acompañamiento"
    private const val DATOS = "Perfil"
    private const val MAPA = "Mapa"
    private const val PLANIFICAR = "Planificar visita"
    private const val RE_PLANIFICAR = "Replanificar"
    private const val ELIMINAR_CALENDARIO = "Elimina tu calendario"

    private const val SEPARADOR = "|"

    private const val COBRANZA = "Datos de la cobranza"
    private const val CONTACTO = "Datos de Contacto"
    private const val ESTADO_CUENTA = "Estado de cuenta"
    private const val MIS_ANOTACIONES = "Mis anotaciones"
    private const val METAS_CONSULTORA = "Metas de la consultora"

    /**pantallas*/

    fun miRuta(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $LISTADO"
    }

    fun dashBoard(): String? {
        return RDD
    }

    fun miMapa(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $MAPA"
    }

//    fun acompaniamiento(rolPersona: Rol?): String? {
//        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $ACOMPANIAMIENTO"
//    }
//
//    fun datos(rolPersona: Rol?): String? {
//        return "$RDD $SEPARADOR ${rolPersona?.comoTextoTag()} $SEPARADOR $DATOS"
//    }

    fun planificar(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $PLANIFICAR"
    }

    fun rePlanificar(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $RE_PLANIFICAR"
    }

    fun eliminarCalendario(): String? {
        return "$RDD $SEPARADOR $MI_RUTA $SEPARADOR $ELIMINAR_CALENDARIO"
    }

    /**eventos*/

    fun eventoMenuRdd(): EventoModel {
        return EventoModel(category = "Menú inferior",
                action = "Clic elemento",
                label = RDD,
                screenName = "Rutas de desarrollo")
    }

    fun eventoMostrarUsuarios(): EventoModel {
        return EventoModel(category = "Usuarios",
                action = "Lista Usuarios",
                label = RDD,
                screenName = "Mostrar Usuarios")
    }


    fun eventoHomeRdd(): EventoModel {
        return EventoModel(category = "Menú bloques",
                action = "Clic elemento",
                label = RDD,
                screenName = "Rutas de desarrollo")
    }

    fun eventoPerfilDatos(): EventoModel {
        return EventoModel(category = RDD,
                action = "Consultora – Clic en tab",
                label = DATOS,
                screenName = "Rutas de desarrollo | Consultora | Acompañamiento")
    }

    fun eventoFinalizarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Registrar visita",
                label = "Finalizar",
                screenName = "Rutas de desarrollo | Acompañamiento | Registrar Visita")
    }

    fun eventoCobranza(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Consultora",
                label = "Tab - $COBRANZA",
                screenName = "Rutas de desarrollo | Consultora | Perfil")
    }

    fun eventoContacto(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Consultora",
                label = "Tab - $CONTACTO",
                screenName = "Rutas de desarrollo | Consultora | Perfil")
    }

    fun eventoEstadoCuenta(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Consultora",
                label = "Tab - $ESTADO_CUENTA",
                screenName = "Rutas de desarrollo | Consultora | Perfil")
    }

    fun eventoMisAnotaciones(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Consultora",
                label = "Tab - $MIS_ANOTACIONES",
                screenName = "Rutas de desarrollo | Consultora | Perfil")
    }

    fun eventoMetasConsultora(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Consultora",
                label = "Tab - $METAS_CONSULTORA",
                screenName = "Rutas de desarrollo | Consultora | Perfil")
    }

    fun eventoVerMas(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA",
                label = "Ver más",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

//    fun eventoCompartirVideo(rol: Rol): EventoModel {
//        return EventoModel(category = RDD,
//                action = "$MI_RUTA - Consultora", //Pendiente debe ser variable consultora
//                label = "Compartir video",
//                screenName = "Rutas de desarrollo | ${rol.codigoRol} | Acompañamiento")
//    }

    fun eventoCrearAcuerdo(): EventoModel {
        return EventoModel(category = RDD,
                action = "$MI_RUTA - Registrar visita",
                label = "Guardar acuerdo",
                screenName = "Rutas de desarrollo | Acompañamiento | Registrar Visita")
    }

    fun eventoReconocer(): EventoModel {
        return EventoModel(category = RDD,
                action = "Comportamiento",
                label = "Reconocer",
                screenName = "Rutas de desarrollo | Comportamientos Observados")
    }

    fun eventoReconocerHabilidades(): EventoModel {
        return EventoModel(category = RDD,
                action = "Habilidades de liderazgo",
                label = "Reconocer",
                screenName = "Rutas de desarrollo | Habilidades de Liderazgo")
    }

    fun eventoPlanificadas(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Clic en tab",
                label = "Planificadas",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoNoPlanificadas(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Clic en tab",
                label = "No planificadas",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoBuscar(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Clic en tab",
                label = "Buscar",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoSwitchMapa(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – switch",
                label = "Mapa",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoSwitchLista(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – switch",
                label = "Lista",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventMarcadorMapa(personaId: Long): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Mapa",
                label = "Seleccionar consultora – $personaId}",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Mapa")
    }

    fun eventoSwitchActivarMapa(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Mapa",
                label = "Switch",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Mapa")
    }

    fun eventoReplanificarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – RePlanificar visita",
                label = "Guardar",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Replanificar visita")
    }

    fun eventoReplanificarAceptarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – RePlanificar visita",
                label = "Ok entendido",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Replanificar visita")
    }

    fun eventoPlanificarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Planificar visita",
                label = "Guardar",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Planificar visita")
    }

    fun eventoPlanificarAceptarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Planificar visita",
                label = "Aceptar",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Planificar visita")
    }

    fun eventoRegistrarVisita(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Consultora",
                label = "Registrar Visita",
                screenName = "Rutas de desarrollo | Consultora | Acompañamiento")
    }

    fun eventoVerMiRuta(): EventoModel {
        return EventoModel(category = RDD,
                action = "Ingresar a sección",
                label = "Ver mi ruta",
                screenName = "Rutas de desarrollo")
    }

    fun eventoMenuRddLlamada(): EventoModel {
        return EventoModel(category = RDD,
                action = "Ingresar a sección",
                label = "Contactar consultora - Teléfono",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddWhatsap(): EventoModel {
        return EventoModel(category = RDD,
                action = "Ingresar a sección",
                label = "Contactar consultora - Whatsapp",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddSMS(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Ver más",
                label = "Contactar consultora - SMS",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddEliminar(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Ver más",
                label = "Elimínala de tu calendario",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddReplanificar(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Ver más",
                label = "Replanifica",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddVisitar(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Ver más",
                label = "Registra tu visita",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoMenuRddMapa(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo – Ver más",
                label = "Ubicar su dirección en el mapa",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Lista")
    }

    fun eventoModalElimiarPlanifica(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo - Calendario",
                label = "Eliminar",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Elimina tu calendario")
    }

    fun eventoModalAceptarEliminar(): EventoModel {
        return EventoModel(category = RDD,
                action = "Mi ruta de desarrollo - Calendario",
                label = "Ok entendido",
                screenName = "Rutas de desarrollo | Mi ruta de desarrollo | Elimina tu calendario")
    }

    fun eventoClickMas(): EventoModel {
        return EventoModel(category = "Menú inferior",
                action = "Click elemento",
                label = "Mas opciones",
                screenName = "")
    }

    fun eventoClickMaterialDesarrollo(): EventoModel {
        return EventoModel(category = "Menú Desplegable - Más opciones",
                action = "Click botón",
                label = "Materiales Desarrollo",
                screenName = "")
    }

    fun eventoClickMDVisualizarODescargar(
            nombreBoton: String,
            nombreDocumento: String): EventoModel {
        return EventoModel(category = "Materiales de Desarrollo",
                action = nombreBoton,
                label = nombreDocumento,
                screenName = "")
    }

    /** constructores */
     fun construirNombrePantalla(tagAnalytics: TagAnalytics, rolPlan: Rol?): String? {
        return when (tagAnalytics) {
            RUTA_DESARROLLO_DASHBOARD -> TagAnalyticsHelper.dashBoard()
            MI_RUTA_DESARROLLO_LISTADO -> TagAnalyticsHelper.miRuta()
            MI_RUTA_MAPA -> TagAnalyticsHelper.miMapa()
            PLANIFICAR_VISITA -> TagAnalyticsHelper.planificar()
            RE_PLANIFICAR_VISITA -> TagAnalyticsHelper.rePlanificar()
            ELIMINA_TU_CALENDARIO -> TagAnalyticsHelper.eliminarCalendario()
            else -> null
        }
    }

    fun construirEvento(tagAnalytics: TagAnalytics): EventoModel {
        return when (tagAnalytics) {
            EVENTO_MOSTRAR_USUARIOS -> TagAnalyticsHelper.eventoMostrarUsuarios()
            EVENTO_HOME_MENU_INFERIOR_MI_RUTA -> TagAnalyticsHelper.eventoMenuRdd()
            EVENTO_HOME_INDICADORES_MI_RUTA -> TagAnalyticsHelper.eventoHomeRdd()
            EVENTO_PERFIL_DATOS -> TagAnalyticsHelper.eventoPerfilDatos()
            EVENTO_MI_RUTA_TAB_PLANIFICADAS -> TagAnalyticsHelper.eventoPlanificadas()
            EVENTO_MI_RUTA_TAB_NO_PLANIFICADAS -> TagAnalyticsHelper.eventoNoPlanificadas()
            EVENTO_MI_RUTA_TAB_BUSQUEDA -> TagAnalyticsHelper.eventoBuscar()
            EVENTO_SWITCH_LISTA -> TagAnalyticsHelper.eventoSwitchLista()
            EVENTO_SWITCH_MAPA -> TagAnalyticsHelper.eventoSwitchMapa()
            EVENTO_SWITCH_ACTIVAR_MAPA -> TagAnalyticsHelper.eventoSwitchActivarMapa()
            EVENTO_RE_PLANIFICAR_VISITA -> TagAnalyticsHelper.eventoReplanificarVisita()
            EVENTO_RE_PLANIFICAR_ACEPTAR_VISITA -> TagAnalyticsHelper.eventoReplanificarAceptarVisita()
            EVENTO_PLANIFICAR_VISITA -> TagAnalyticsHelper.eventoPlanificarVisita()
            EVENTO_PLANIFICAR_ACEPTAR_VISITA -> TagAnalyticsHelper.eventoPlanificarAceptarVisita()
            EVENTO_VER_MI_RUTA_MAPA -> TagAnalyticsHelper.eventoVerMiRuta()
            EVENTO_MENU_MI_RUTA_WHATSAPP -> TagAnalyticsHelper.eventoMenuRddWhatsap()
            EVENTO_MENU_MI_RUTA_LLAMAR -> TagAnalyticsHelper.eventoMenuRddLlamada()
            EVENTO_MENU_MI_RUTA_SMS -> TagAnalyticsHelper.eventoMenuRddSMS()
            EVENTO_MENU_MI_RUTA_ELIMINAR -> TagAnalyticsHelper.eventoMenuRddEliminar()
            EVENTO_MENU_MI_RUTA_REPLANIFICAR -> TagAnalyticsHelper.eventoMenuRddReplanificar()
            EVENTO_MENU_MI_RUTA_VISITA -> TagAnalyticsHelper.eventoMenuRddVisitar()
            EVENTO_MENU_MI_RUTA_MAPA -> TagAnalyticsHelper.eventoMenuRddMapa()
            EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA -> TagAnalyticsHelper.eventoModalElimiarPlanifica()
            EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA_ACEPTAR -> TagAnalyticsHelper.eventoModalAceptarEliminar()
            EVENTO_COBRANZA -> TagAnalyticsHelper.eventoCobranza()
            EVENTO_CONTACTO -> TagAnalyticsHelper.eventoContacto()
            EVENTO_ESTADO_CUENTA -> TagAnalyticsHelper.eventoEstadoCuenta()
            EVENTO_MIS_ANOTACIONES -> TagAnalyticsHelper.eventoMisAnotaciones()
            EVENTO_METAS_CONSULTORA -> TagAnalyticsHelper.eventoMetasConsultora()
            EVENTO_VER_MAS -> TagAnalyticsHelper.eventoVerMas()
            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoPorRol(tagAnalytics: TagAnalytics, sesion: Sesion, rol: Rol): EventoModel {
        val rolSesion = sesion.rol
        return when (tagAnalytics) {
            EVENTO_FINALIZAR_VISITA -> TagAnalyticsHelper.eventoFinalizarVisita()
            EVENTO_CREAR_ACUERDO -> TagAnalyticsHelper.eventoCrearAcuerdo()
            EVENTO_REGISTRAR_VISITA -> TagAnalyticsHelper.eventoRegistrarVisita()
            EVENTO_RECONOCER_COMPORTAMIENTO -> TagAnalyticsHelper.eventoReconocer()
            EVENTO_RECONOCER_HABILIDADES -> TagAnalyticsHelper.eventoReconocerHabilidades()
                    ?: throw AnalyticsEventException()

            else -> throw AnalyticsEventException()
        }
    }

    fun construirEventoPorId(personaId: Long): EventoModel {
        return TagAnalyticsHelper.eventMarcadorMapa(personaId)
    }

    fun construirEvento2(tagAnalytics: TagAnalytics, nombreBoton: String, nombreDocumento: String): EventoModel {
        return when (tagAnalytics) {
            TagAnalytics.EVENTO_MATERIAL_DE_DESARROLLO_VISUALIZAR_Y_DESACARGAR ->
                TagAnalyticsHelper.eventoClickMDVisualizarODescargar(nombreBoton, nombreDocumento);
            else -> throw AnalyticsEventException()
        }
    }

}