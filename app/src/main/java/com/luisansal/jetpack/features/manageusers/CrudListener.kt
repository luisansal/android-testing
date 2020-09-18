package com.luisansal.jetpack.features.manageusers

interface CrudListener<T> {

    fun onList()

    fun onNew()

    fun onEdit()

    fun setOBjects(oBjects: List<T>)
}
