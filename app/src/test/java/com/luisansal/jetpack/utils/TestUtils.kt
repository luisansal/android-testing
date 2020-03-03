package com.luisansal.jetpack.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.io.IOException
import java.util.*

@Throws(IOException::class)
fun Any.readString(path: String): String {
    val stream = this::class.java.classLoader?.getResourceAsStream(path)
    val s = stream?.let { Scanner(it).useDelimiter("\\A") } ?: return ""
    val result = if (s.hasNext()) s.next() else ""
    stream.close()
    return result
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

fun <T> List<T>.mockPagedList(): PagedList<T> {
    val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
    Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
        val index = invocation.arguments.first() as Int
        this[index]
    }
    Mockito.`when`(pagedList.size).thenReturn(this.size)
    return pagedList
}