package com.curelator.headache.test

import androidx.room.Room
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.curelator.headache.common.api.Network
import com.curelator.headache.common.di.AppModule
import com.curelator.headache.common.di.add
import com.curelator.headache.common.di.module
import com.curelator.headache.common.storage.AppDatabase
import com.curelator.headache.diary.di.DiaryModule
import com.curelator.headache.treatment.di.TreatmentModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.coroutines.Dispatchers
import org.mockito.Mockito
import kotlin.coroutines.CoroutineContext

interface InjectedTest {
//    fun withModule(module: Kodein.Module, block: (Kodein) -> Unit) {
//        val kodein = ConfigurableKodein(mutable = true)
//        kodein.add(AppModule(InstrumentationRegistry.getInstrumentation().targetContext))
//        kodein.add(InMemoryDatabaseModule())
//        kodein.add(AsyncInUiThreadModule())
//        kodein.add(DiaryModule())
//        kodein.add(AlwaysConnectedNetworkModule())
//        kodein.add(TreatmentModule())
//        kodein.add(module)
//        block(kodein)
//    }
}

private val inMemoryDatabase: AppDatabase by lazy {
    Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}

private object InMemoryDatabaseModule {
    operator fun invoke() = module {
        bind<AppDatabase>() with instance(inMemoryDatabase)
    }
}

private object AsyncInUiThreadModule {
    operator fun invoke() = module {
        bind<CoroutineContext>() with instance(Dispatchers.Main)
    }
}

private object AlwaysConnectedNetworkModule {
    operator fun invoke() = module {
        val contextMock = Mockito.mock(Context::class.java)
        bind<Network>() with instance(AlwaysConnectedNetwork(contextMock))
    }
}
