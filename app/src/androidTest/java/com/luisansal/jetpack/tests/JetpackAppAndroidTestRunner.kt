package com.luisansal.jetpack.tests

import cucumber.api.CucumberOptions
import cucumber.api.android.CucumberAndroidJUnitRunner

@CucumberOptions(glue = ["com.luisansal.jetpack.tests"], features = ["features"], tags = ["~@wip"])
class JetpackAppAndroidTestRunner : CucumberAndroidJUnitRunner()