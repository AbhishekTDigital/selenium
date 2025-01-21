package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SelectZipCodePage 
{
	WebDriver ldriver;
	
	//Constructor
		public SelectZipCodePage(WebDriver rDriver)
		{
			ldriver=rDriver;
			
			PageFactory.initElements(rDriver, this);
		}

}
