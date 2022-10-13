package com.luisansal.jetpack.base

import com.luisansal.jetpack.MyApplication
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.di.injectIntegrationTestModules


class AppTest : MyApplication() {

    override fun injectModules(){
        injectIntegrationTestModules(this)
        BaseRoomDatabase.getDatabase(this)?.isTest = true
    }

}
