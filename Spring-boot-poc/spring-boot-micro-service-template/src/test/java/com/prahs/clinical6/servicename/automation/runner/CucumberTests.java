package com.prahs.clinical6.servicename.automation.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Cucumber runner class for DevIntegration Tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, plugin = { "pretty", "html:target/cucumber",
    "json:target/cucumber-report.json",
    "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/ExtentReport.html" }, glue = {
        "com/prahs/clinical6/servicename/automation/stepdefs" }, features = {
            "src/test/resources/features" })
public class CucumberTests {
}
