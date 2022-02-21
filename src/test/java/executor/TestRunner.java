package executor;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//@Runwith(cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features"
        ,glue = {"stepDefinition"}
        ,plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,monochrome = true
        ,publish = true

)
public class TestRunner extends AbstractTestNGCucumberTests  {


}
