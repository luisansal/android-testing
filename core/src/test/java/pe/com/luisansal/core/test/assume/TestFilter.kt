package pe.com.luisansal.core.test.assume

import org.junit.Assume.assumeTrue
import pe.com.luisansal.core.test.CurrentTestFilterMode
import pe.com.luisansal.core.test.Screenshot
import pe.com.luisansal.core.test.TestFilterMode
import java.lang.reflect.Method

interface TestFilter {

    companion object {
        private const val ONLY_SCREENSHOT_MESSAGE =
            "Test is not tagged with the @Screenshot annotation and the runner is configured" +
                "to run only screenshot tests"

        private const val ONLY_NOT_SCREENSHOT_MESSAGE =
            "Test is tagged with the @Screenshot annotation and the runner is configured" +
                "to run only tests that are not screenshot"
    }

    fun verifyTypeOfTestIsCorrect(methodName: String) {
        when (CurrentTestFilterMode) {
            TestFilterMode.OnlyScreenshot ->
                verifyTestMethod(methodName, ONLY_SCREENSHOT_MESSAGE) {
                    it.isAnnotationPresent(Screenshot::class.java)
                }
            TestFilterMode.OnlyNotScreenshot ->
                verifyTestMethod(methodName, ONLY_NOT_SCREENSHOT_MESSAGE) {
                    !it.isAnnotationPresent(Screenshot::class.java)
                }
            TestFilterMode.All -> Unit
        }
    }

    private fun verifyTestMethod(
        methodName: String,
        message: String,
        assumption: (Method) -> Boolean
    ) {
        val isAssumptionTrue = try {
            val method = javaClass.getMethod(methodName)
            assumption(method)
        } catch (e: NoSuchMethodException) {
            false
        } catch (e: ClassNotFoundException) {
            false
        }

        assumeTrue(message, isAssumptionTrue)
    }
}
