package com.luisansal.jetpack.core.utils

inline fun <reified T : Any> listByElementsOf(vararg elements: Any): List<T> {
    val mutableList = mutableListOf<T>()
    elements.forEach { element ->
        if (element is T) {
            mutableList += element
        } else if (element is List<*>) {
            mutableList += element.mapNotNull { it as? T }
        }
    }
    return mutableList
}

infix fun <T> MutableIterable<T>.extract(predicate: (T) -> Boolean): List<T> {
    val result = this.filter(predicate)

    this.removeAll(predicate)

    return result
}

fun <T> MutableList<T>.addIfNotNull(value: T?) {
    if (value != null)
        this.add(value)
}
