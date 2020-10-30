package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.analytics.PantallaModel
import com.luisansal.jetpack.data.datastore.FirebaseAnalyticsCloudDataStore
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository

class FirebaseAnalyticsDataRepository(private val analyticsCloudDataStore: FirebaseAnalyticsCloudDataStore) : FirebaseAnalyticsRepository {

    override fun enviarEvento(eventoModel: EventoModel): EventoModel {
        return analyticsCloudDataStore.enviarEvento(eventoModel)
    }

    override fun enviarPantalla(pantallaModel: PantallaModel) {
        analyticsCloudDataStore.enviarPantalla(pantallaModel)
    }
}