package com.luisansal.jetpack.base

import com.luisansal.jetpack.di.injectIntegrationTestModules
import com.luisansal.jetpack.ui.MyApplication


class AppTest : MyApplication() {

    override fun injectModules(){
        injectIntegrationTestModules(this)
    }

}
