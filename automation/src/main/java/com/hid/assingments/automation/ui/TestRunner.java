package com.hid.assingments.automation.ui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/resources/features", glue = "com.hid.assingments.automation.ui")
public class TestRunner extends AbstractTestNGCucumberTests {
}
