package com.luisansal.jetpack.ui.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


fun Fragment.getFragmentNavController(@IdRes id: Int) : NavController {
    return Navigation.findNavController(this.requireActivity(), id)
}