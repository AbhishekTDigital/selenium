package TestRunner;
 // Ensure this matches the glue path in @CucumberOptions

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
    features = {
            ".//Features/LoginFeature.feature", 
            ".//Features/AddAgentFeature.feature",
            ".//Features/CreateTerritoriesFeature.feature", 
            ".//Features/ChangeDispositionMapToTableFeature.feature",
            ".//Features/LeadDispositionTableToMapFeature.feature",
            ".//Features/GetZipCodesFeature.feature",
            ".//Features/SelectZipCodeFeature.feature",
            ".//Features/AgentStreetSortingFeature.feature",
            ".//Features/DragAndDropAgentDataFeature.feature",
    }, 
    glue = "StepDefinition",
    dryRun = false,
    monochrome = true,
    plugin = {"pretty", "html:target/cucumber-reports/reports_html.html"}
)
public class Run extends AbstractTestNGCucumberTests {
 /*   public void createDriver() {  // Removed the @Override annotation
        // Set up the ChromeDriver path (optional if already set in the environment)
//        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        // Now, you can use this driver in your test steps.
    } */
}
