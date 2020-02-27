package com.luisansal.jetpack.base

import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.di.injectIntegrationTestModules
import com.luisansal.jetpack.ui.MyApplication


class AppTest : MyApplication() {

    override fun injectModules(){
        injectIntegrationTestModules(this)
        BaseRoomDatabase.getDatabase(this)?.isTest = true
    }

}
