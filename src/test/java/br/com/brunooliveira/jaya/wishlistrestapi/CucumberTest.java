package br.com.brunooliveira.jaya.wishlistrestapi;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@ActiveProfiles("local")
@CucumberOptions(features = "src/test/resources/")
public class CucumberTest {

}