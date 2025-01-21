package StepDefinition;

import io.cucumber.java.en.*;

public class CloseStep extends BaseClass
{
	@Then("close browser")
	public void close_browser() {
		staticWait(2000);
		driver.close();
		log.info("Browser closed");

		//driver.quit();
	}

	
}
