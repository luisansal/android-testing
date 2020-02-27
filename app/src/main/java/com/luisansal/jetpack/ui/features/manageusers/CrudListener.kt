package com.luisansal.jetpack.ui.features.manageusers

interface CrudListener<T> {

    fun onList()

    fun onNew()

    fun onEdit()

    fun setOBjects(oBjects: List<T>)
}
