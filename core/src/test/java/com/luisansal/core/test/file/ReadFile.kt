package com.luisansal.core.test.file

import androidx.test.platform.app.InstrumentationRegistry
import org.apache.commons.io.IOUtils
import java.nio.charset.Charset

fun getContentFromFile(path: String? = null): String {
    if (path == null) {
        return ""
    }

    val stream = InstrumentationRegistry.getInstrumentation().context.assets.open(path)
    return IOUtils.toString(stream, Charset.defaultCharset())
}
