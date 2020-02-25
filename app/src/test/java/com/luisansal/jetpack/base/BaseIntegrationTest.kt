package com.luisansal.jetpack.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Ignore
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.annotation.Config

@Ignore("BaseIntegrationTest")
@RunWith(AndroidJUnit4::class)
@Config(application = AppTest::class, sdk = [27], manifest = Config.NONE)
abstract class BaseIntegrationTest : AutoCloseKoinTest()
