package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GetZipCodesPage 
{
	WebDriver ldriver;

	//Constructor
	public GetZipCodesPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	

}
