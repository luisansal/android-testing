package com.luisansal.jetpack.common.interfaces

import com.luisansal.jetpack.domain.entity.User

interface CrudListener<T> {

    var oBject: User?

    val objects: List<User>?
    fun onList()

    fun onNew()

    fun onEdit()

    fun setOBjects(oBjects: List<T>)
}
