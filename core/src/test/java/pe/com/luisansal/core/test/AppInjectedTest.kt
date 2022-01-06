package pe.com.luisansal.core.test

//import com.curelator.headache.asApp
//import com.curelator.headache.common.api.Network
//import com.curelator.headache.common.di.module
//import com.curelator.headache.common.di.overriding
//import com.github.salomonbrys.kodein.Kodein
//import com.github.salomonbrys.kodein.bind
//import com.github.salomonbrys.kodein.instance
//import kotlinx.coroutines.android.asCoroutineDispatcher
//import org.joda.time.DateTimeZone
//import org.junit.Before
//import org.mockito.MockitoAnnotations

abstract class AppInjectedTest {

//    internal val coroutineHandler = SwitchHandler(Handler(Looper.getMainLooper()))
//    abstract val testDependencies: Kodein.Module

//    @Before
    open fun setUp() {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT"))
//        DateTimeZone.setDefault(DateTimeZone.forID("GMT"))
//        MockitoAnnotations.initMocks(this)
//        val app = headacheApp()
//        app.resetInjection()
//        app.overrideModule = module {
//            overriding(testDependencies)
//            bind<CoroutineContext>() with instance(coroutineHandler.asCoroutineDispatcher("Testing"))
//            bind<CoroutineContext>("singleThread") with
//                instance(coroutineHandler.asCoroutineDispatcher("Single Thread Testing"))
//            bind<Network>() with instance(
//                AlwaysConnectedNetwork(getInstrumentation().targetContext)
//            )
//        }
    }

//    fun headacheApp() = getInstrumentation().targetContext.asApp()
}
