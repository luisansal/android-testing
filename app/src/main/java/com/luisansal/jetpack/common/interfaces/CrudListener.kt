package com.luisansal.jetpack.common.interfaces

interface CrudListener<T> {

    fun onList()

    fun onNew()

    fun onEdit()

    fun setOBjects(oBjects: List<T>)
}
