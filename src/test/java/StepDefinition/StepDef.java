package StepDefinition;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import PageObject.AddAgentPage;
import PageObject.AgentLeadFilterPage;
import PageObject.AgentStreetSortingPage;
import PageObject.ChangeDispositionMapToTablePage;
import PageObject.CreateTerritoriesPage;
import PageObject.DragAndDropAgentDataPage;
import PageObject.GetZipCodesPage;
import PageObject.LeadDispositionTableToMapPage;
import PageObject.LoginPage;
import PageObject.SelectZipCodePage;
import Utilities.ReadConfig;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

//Child class of Base Class
public class StepDef extends BaseClass
{
	int leadNumber;
	String leadDisposition1;
	String scndLeadDispos;
	String audienceId;
	String primaryDrop;
	String secondaryDrop;
	
	private String alocNumberAfterSorting; // Variable to store ALOC Number after sorting
    private String alocNumberAfterReLogin; // Variable to store ALOC Number after re-login
    int initialLeadCount;
    int tempLeadCount;
    String mapAudId;
    String tabAudId;
    
 // Create a Faker object
    Faker faker = new Faker();

	
	@Before
	public void setup()
	{
		//properties file initialization
		readConfig=new ReadConfig();
		
		
		
		//initialization of log class
		log=LogManager.getLogger("StepDef");
		
		System.out.println("setup method executed...");
		
		//read browser name from readconfig method
		String browser=readConfig.getBrowser();
		
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
		
		//launch browser
				switch(browser.toLowerCase())
				{
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					break;

				case "msedge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					break;

				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				default:
					driver = null;
					break;

				}
						
				log.info("Setup1 executed...");
			}
			
			@Given("User Launch Chrome browser")
			public void user_launch_chrome_browser() {

					
				loginPg=new LoginPage(driver);
				addAgentPg=new AddAgentPage(driver);
				createTerritoriesPg=new CreateTerritoriesPage(driver);
				chngDisposMapToTabPg=new ChangeDispositionMapToTablePage(driver);
				leadDisposTabToMapPg=new LeadDispositionTableToMapPage(driver);
				getZipCodesPg=new GetZipCodesPage(driver);
				selectZipCodePg=new SelectZipCodePage(driver);
				agentStreetSortingPg=new AgentStreetSortingPage(driver);
				dragAndDropAgentDataPg=new DragAndDropAgentDataPage(driver);
				agentLeadFilterPg=new AgentLeadFilterPage(driver);
				
				log.info("Chrome browser launched...");
			}

			@When("User opens URL {string}")
			public void user_opens_url(String url) {
				
				driver.get(url);
				log.info("url opened...");
			}
			
			//////////////////Login ////////////////
			////////////////////////////////////////////
			@When("User enters ATNT Email as {string}")
			public void user_enters_atnt_email_as(String atntUser) {
//				staticWait(7000);
				//move on login Window
						String origionalWindow=driver.getWindowHandle();
						Set<String> allPages=driver.getWindowHandles();
						for(String page:allPages)
						{
							driver.switchTo().window(page);
						}
						System.out.println("move to login window");
						dynamicWebDriverWait(By.xpath("//input[@id='username']"),60);
						loginPg.enterUserName(atntUser);
						log.info("Entered Email...");
			}
			@When("User enters ATNT Password as {string}")
			public void user_enters_atnt_password_as(String atntPass) {
				loginPg.enterATNTPassword(atntPass);
				log.info("Entered Password...");
			}
			
			@When("Click on Login to sandbox")
			public void click_on_login_to_sandbox() {
				loginPg.clickOnLoginButton();
				log.info("Clicked On Login to sandbox...");
				staticWait(5000);
			}
			
			@When("Click on Allow Access button")
			public void click_on_allow_access_button() {
				loginPg.clickOnAllowAccessButton();
				log.info("Clicked On Allow Access button...");
//				staticWait(10000);
				dynamicWebDriverWait(By.xpath("//aside[@class='main-sidebar elevation-4 sidebar-dark-primary left-sidebar']"),60);
			}
			
			//////////////Login Page Title Verified///////////////////
			@Then("Verify Page Title as {string}")
			public void verify_page_title_as(String title) {
			System.out.println("read Data from File:"+title);
			String actualTitle=driver.getTitle();
		    System.out.println("read actual Title :"+actualTitle);
		    String expectedTitle=title;
		    
		    Assert.assertEquals(expectedTitle, actualTitle);
		    System.out.println("Title Verified");
		    log.info("Verify Title....");
		    
			
			}
			
			/////////////////Verify Login Page Logo////////////
			@Then("Verify Page Logo")
			public void verify_page_logo() {
				 WebElement logo = driver.findElement(By.xpath("//pf-image[@class='sc-gTRrQi kDtyhN hydrated']")); // Replace with actual ID or other locator

		            // Verify logo presence
		            if (logo.isDisplayed()) {
		                System.out.println("Logo is displayed on the page.");

		                // Verify logo's source attribute
		                String logoSrc = logo.getAttribute("src");
		                if (logoSrc.contains("https://i.ibb.co/XDjSrGn/at-t-logo-brandlogos-net-57cuk.png")) { // Replace with the expected image name or URL
		                    System.out.println("Logo source is correct: " + logoSrc);
		                } else {
		                    System.out.println("Logo source is incorrect: " + logoSrc);
		                }

		                // Verify logo's alt text
		                String logoAlt = logo.getAttribute("alt");
		                if (logoAlt.equals("AT&T Logo")) { // Replace with the expected alt text
		                    System.out.println("Logo alt text is correct: " + logoAlt);
		                } else {
		                    System.out.println("Logo alt text is incorrect: " + logoAlt);
		                }
		            } else {
		                System.out.println("Logo is not displayed on the page.");
		            }
			}
			
			@When("click on User Icon")
			public void click_on_user_icon() {
				staticWait(6000);
				loginPg.clickOnUserIcon();				
				log.info("Clicked on User Icon....");
				staticWait(2000);
			}
			
			@When("click on Logout button")
			public void click_on_logout_button() {
				switchToNewWindow("currentWindow");
				loginPg.clickOnLogoutButton();				
				log.info("Clicked on LogOut Button....");
				staticWait(5000);
			}
			
			//////////////Add Agent///////////////////
			/////////////////////////////////////////////////////
			@When("click on Add Agent button")
			public void click_on_add_agent_button() {
			    addAgentPg.clickOnAddAgentButton();
			    log.info("Clicked on Add Agent Button....");
			    staticWait(2000);
			}
			
			@When("Enter First Name as {string}")
			public void enter_first_name_as(String fstName) {
				staticWait(1000);
				// Generate random first and last names
		        String firstName = faker.name().firstName();
				
				WebElement element=driver.findElement(By.id("firstName"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
			    addAgentPg.enterFirstName(firstName);
			    log.info("Entered First Name...."+firstName);
			}
			
			@When("Enter Last Name as {string}")
			public void enter_last_name_as(String LName) {
				staticWait(1000);	
				 String lastName = faker.name().lastName();
				addAgentPg.enterLastName(lastName);
				log.info("Entered Last Name...."+lastName);
			}
			
			@When("Enter Phone Number as {string}")
			public void enter_phone_number_as(String number) {
				staticWait(1000);
				addAgentPg.enterPhoneNumber(number);
				log.info("Entered Phone Number....");
			}
			
			@When("Enter Email as {string}")
			public void enter_email_as(String Email) {
				staticWait(1000);
				addAgentPg.enterEmail(Email);
				log.info("Entered Email....");
			}
			
			@When("click on Create button")
			public void click_on_create_button() {
				addAgentPg.clickOnCreateButton();
				log.info("Clicked On Create button....");
				staticWait(1000);
			}
			
			//////Add Agent Verification Msg //////////////////////////////////
			@Then("Verify Msg as {string}")
			public void verify_msg_as(String addAgent) {
				System.out.println("read data from file:"+addAgent);
//				switchToNewWindow("currentWindow");
				String origionalWindow=driver.getWindowHandle();
				Set<String> allPages=driver.getWindowHandles();
				for(String page:allPages)
				{
					driver.switchTo().window(page);
				}
				System.out.println("Move to msg window..");
				
				staticWait(1000);
//		        dynamicWebDriverWait(By.xpath("//div[text()='Data submitted for adding Agent']"),10);
		        
		        //Text Msg For Validation//OneDrive Msg //OneDrive folders saved successfully!
		        String actualMsg=addAgentPg.clickOnVerifyMsg();
		        System.out.println("read actualMsg :"+actualMsg);
		        String expectedMsg=addAgent;
		        
		        Assert.assertEquals(expectedMsg, actualMsg);
		        System.out.println("add Agent Msg Verified");
		        log.info("Verify Add Agent Msg....");
		        staticWait(6000);
			}
			
			////////////////Create Territories////////////////////////////
			///////////////////////////////////////////////////////////////////
			@Then("click on Zip Codes")
			public void click_on_zip_codes() {
				createTerritoriesPg.clickOnZipCodes();
				log.info("Clicked on Zip Codes....");
				staticWait(3000);
			}
			
			@Then("select Zip Codes as {string}")
			public void select_zip_codes_as(String zipCode) {
//				selectOptionByText(By.xpath("//input[@type='checkbox']"),zipCheckbox); 01002
//				driver.findElement(By.xpath("//input[@value='01030']")).click();//01050
				clickCheckboxByText(zipCode);
				staticWait(2000);
				log.info(" Checkbox Selected....");
				
			}
			
			@Then("click on Select Zip Codes button")
			public void click_on_select_zip_codes_button() {
				createTerritoriesPg.clickOnZipCodeButton();
				log.info(" Clicked on Select zip codes button....");
				staticWait(6000);
				
			}
			
			@Then("user click on Polygon Icon")
			public void user_click_on_polygon_icon() {
				////move on map Window
//				String handle=driver.getWindowHandle();
				Set<String> allPages=driver.getWindowHandles();
				for(String page: allPages)
				{
					driver.switchTo().window(page);
				}
				///Move to assign element mouse action
				WebElement element=driver.findElement(By.xpath("//div[text()='Assign']"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
				
				dynamicWebDriverWait(By.xpath("//a[@title='Draw a polygon']"),60);
				System.out.println("Wait for Map Loading........");
				createTerritoriesPg.clickOnPolygonIcon();
				log.info(" Clicked on Polygon Icon....");
				staticWait(5000);
			}
			
			///////////////////draw Polygon//////////////
			@Then("user draw a Polygon")
			public void user_draw_a_polygon() {
//				driver.findElement(By.xpath("//span[text()='+']")).click();
//			    staticWait(5000);
//			    driver.findElement(By.xpath("//span[text()='+']")).click();
			    staticWait(2000);
//			    driver.findElement(By.xpath("//span[text()='+']")).click();
//			    zoomOutMapUsingKeyboard(1);
			    // Activate the polygon drawing tool
			 // Initialize Actions class object to perform mouse actions
	            Actions builder = new Actions(driver);

	            // Locate map area, assuming it's uniquely identified for actions
	            WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = -100; // base X coordinate   X-axis (width): 0 to 1919
	            int baseY = 80; // base Y coordinate   Y-axis (height): 0 to 1079
	            int offset = 140;  // offset to create multiple points

	            // Click to start drawing the polygon
	            builder.moveToElement(mapArea, baseX, baseY).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX + offset, baseY).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX + offset, baseY + offset).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX, baseY + offset).click().perform();
	            staticWait(3000);

	            // Complete the polygon by clicking on the first point again
	            builder.moveToElement(mapArea, baseX, baseY).click().perform();
	            log.info("Created Polygon....");
	            staticWait(3000);
	            System.out.println("draw polygone....");
	            staticWait(5000);
			
			}
			
			@Then("click on Assign button")
			public void click_on_assign_button() {
//				String leadNumber=driver.findElement(By.xpath("(//div[@class='button-text'])[1]")).getText();
//				System.out.println("Lead Number:"+leadNumber);
				
//				 WebElement element = driver.findElement(By.xpath("(//div[@class='button-text'])[1]"));

			        // Get the text of the element
//			        String text = element.getText();
				 // Extract the number inside square brackets
	             leadNumber = getNumberInsideBrackets(By.xpath("(//div[@class='button-text'])[1]"));

	            // Print the extracted number
	            System.out.println("Number inside brackets: " + leadNumber);
				
				createTerritoriesPg.clickOnAssignButton();
				log.info(" Clicked on Assign button....");
				staticWait(5000);
			}
			
			@Then("enter Territory Name as {string}")
			public void enter_territory_name_as(String terriName) {
				createTerritoriesPg.enterTerritoryName(terriName);
				log.info("entered Territory Name....");
				
			}
			
			@Then("enter Completion Date as {string}")
			public void enter_completion_date_as(String datepicker) throws AWTException {
				createTerritoriesPg.enterCompletionDate(datepicker);
				Robot rb=new Robot();
				rb.delay(2000);
				rb.keyPress(KeyEvent.VK_ESCAPE);
				rb.keyRelease(KeyEvent.VK_ESCAPE);
				log.info("entered Completeion Date....");
				staticWait(5000);
			}
			
			@Then("select Assign Agent Dropdown as {string}")
			public void select_assign_agent_dropdown_as(String agentName) throws AWTException {
				createTerritoriesPg.enterOnAssignAgentDropdown(agentName);
				//press escape button			 							 
				
				Robot rb=new Robot();
				rb.delay(2000);
				rb.keyPress(KeyEvent.VK_ESCAPE);
				rb.keyRelease(KeyEvent.VK_ESCAPE);
				
				
//				staticWait(2000);
//				selectOptionByText(By.xpath(""),agentName)
				log.info("entered Assign Agent....");
				
				staticWait(2000);
				System.out.println("select Agent dropdown.............");
				
			}
			
			@Then("click on save button")
			public void click_on_save_button() {
				createTerritoriesPg.clickOnSaveButton();
				log.info("Clicked on Save Button....");
				staticWait(5000);
			}
			
/*			@Then("user Verify Msg as {string}")
			public void user_verify_msg_as(String createTerriMsg) {
				System.out.println("read data from file:"+createTerriMsg);
//		        dynamicWebDriverWait(By.xpath("//div[text()='Territory Saved Successfully!']"),30);
				dynamicWebDriverWait(By.xpath("(//div[text()='Territory Saved Successfully!'])[2]"),30);
		        
		        //Text Msg For Validation//OneDrive Msg //OneDrive folders saved successfully!
		        String actualMsg=createTerritoriesPg.verifyCreateTerritoryMsg();
		        System.out.println("read actualMsg :"+actualMsg);
		        String expectedMsg=createTerriMsg;
		        
		        Assert.assertEquals(expectedMsg, actualMsg);
		        System.out.println("Create Territory Msg Verified");
		        log.info("Verify Create Territory Msg....");
		        staticWait(6000);
			}  */
			
			/////////////////////////////Login Again With Different user For validation
			@Then("User Login again with Agent username")
			public void user_login_again_with_agent_username() {
				staticWait(5000);
//				switchToNewWindow("currentWindow");
				Set<String> allWindowHandles = driver.getWindowHandles();

				// Iterate through the handles and switch to the last one
				for (String windowHandle : allWindowHandles) {
				    driver.switchTo().window(windowHandle);
				}
//				staticWait(2000);
				dynamicWebDriverWait(By.xpath("//img[@id='logo']"),60);
				System.out.println("Wait for Login ..");
//		        driver.findElement(By.xpath("//div[text()='Use another account']")).click();
		        log.info("User Logged again with different user....");
		    }
			
			//////////////////////////////////////////
			@When("click on Assignment")
			public void click_on_assignment() {
				createTerritoriesPg.clickOnAssignment();
				log.info("Clicked on Assignment ....");
				staticWait(3000);
			}
			
			@Then("select Territories List checkbox as {string}")
			public void select_territories_list_checkbox_as(String terricheqbox) {
				
				System.out.println("read from file:"+terricheqbox);
				staticWait(3000);
				
				clickCheckboxByText(terricheqbox);
				log.info("Selected Checkbox....");
				System.out.println("clicked On checkBox....");
				

			}
			
			@When("click on List button")
			public void click_on_list_button() {
				dynamicWebDriverWait(By.xpath("//button[text()='List']"),30);
				createTerritoriesPg.clickOnMapButton();
				log.info("Clicked on List Button ....");
				staticWait(5000);
			}
			
			/////////////////verify Table row count/////////////
			@Then("user Verify the Table row count")
			public void user_verify_the_table_row_count() {
				getTableRowCount("MuiTable-root css-1owb465");
				System.out.println("read table row count........");
				verifyTableRowCount("MuiTable-root css-1owb465", leadNumber);
				System.out.println("verify table row count......."+leadNumber);
				log.info("Verified Table Row Number....");
			}
			
			////////////////// Change Disposition on Map appear on Table ////////////////////////////
			@When("user Zoom In on Map")
			public void user_zoom_in_on_map() {
				///move on map Window/////////
				
				dynamicWebDriverWait(By.xpath("//button[text()='List']"),20);
				System.out.println("wait for list button....");
				Set<String> allWindowHandles = driver.getWindowHandles();

				// Iterate through the handles and switch to the last one
				for (String windowHandle : allWindowHandles) {
				    driver.switchTo().window(windowHandle);
				}
				System.out.println("switch on Map Window");
				////mouse over action
				WebElement element=driver.findElement(By.xpath("//button[text()='List']"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
				
				/////click On Map Coordinate
				WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = 50; // base X coordinate
	            int baseY = 80; // base Y coordinate
//	            int offset = 140;  // offset to create multiple points

	            // Click to start drawing the polygon
	            act.moveToElement(mapArea, baseX, baseY).click().perform();
	            System.out.println("Clicked on map Window....");
	            staticWait(2000);
					zoomInMapUsingKeyboard(2);
					System.out.println("Zoom in Complete....");
					log.info("Map Zoom Success...");
					staticWait(2000);		
			}
			
			@Then("user click on Lead Coordinate as {string}")
			public void user_click_on_lead_coordinate_as(String number) {
				staticWait(5000);
				driver.findElement(By.xpath("(//img[@role='button'])["+number+"]")).click();
				log.info("Clicked on Lead Coordinate...");
				staticWait(5000);
			} 
			
			@Then("user get Audience Id")
			public void user_get_audience_id() {
				List<List<String>> tableData =getTableData(By.xpath("//table[@class='marker-table']"));
				for (List<String> row : tableData) {
			        System.out.println(row); // Print each row
			        
			        mapAudId= chngDisposMapToTabPg.getAudienceId();
//			        this.audienceiD=audienceiD;
			        System.out.println("Audience Id:"+chngDisposMapToTabPg.getAudienceId());
			    }
				
				
/*				List<WebElement> rowList=driver.findElements(By.xpath("//table[@class='marker-table']/tbody/tr"));
				System.out.println("Rows:"+rowList.size());
				List<WebElement> columnList=driver.findElements(By.xpath("//table/tbody/tr[1]/td"));
				System.out.println("Rows:"+columnList.size());
				for(int r=1;r<=rowList.size();r++)
				{
					for(int c=1;c<=columnList.size();c++)
					{
						String data=driver.findElement(By.xpath("//table/tbody/tr["+r+"]/td["+c+"]")).getText();
						System.out.println(data);
					}
				}  */
				log.info("Get Audience Id Success...");
			}
			
			@Then("user select Lead Disposition as {string}")
			public void user_select_lead_disposition_as(String leadDisposition1) {
				this.leadDisposition1=leadDisposition1;
				selectDropdownByVisibleText(By.xpath("(//select)[1]"),leadDisposition1);
				log.info("Selected primary lead Disposition...");
				staticWait(3000);
			}
			
			@Then("user select Lead Disposition secondary as {string}")
			public void user_select_lead_disposition_secondary_as(String scndLeadDispos) {
				this.scndLeadDispos=scndLeadDispos;
				selectDropdownByVisibleText(By.xpath("(//select)[2]"),scndLeadDispos);
				log.info("Selected secondary lead Disposition...");
				staticWait(5000);
			}
			
			@When("user enter Audience Id in search Box as {string}")
			public void user_enter_audience_id_in_search_box_as(String audienceId) {
				this.audienceId=audienceId;
				///change window 
				Set<String> allPages=driver.getWindowHandles();
				for(String page: allPages)
				{
					driver.switchTo().window(page);
				}
				chngDisposMapToTabPg.entersAudienceIdInSearchbox(mapAudId);
				log.info("Entered Audience id in search box...");
				staticWait(5000);
			}
			
			/////////verify Lead Disposition Table Data
//			@Then("user Verify Lead Disposition Table Data")
//			public void user_verify_lead_disposition_table_data() {
/*				String leadDis1=chngDisposMapToTabPg.getLeadDisposition1();
				System.out.println("Actual table Data:"+leadDis1);
				 Assert.assertEquals(leadDisposition, leadDis1);
				 System.out.println("Lead Dispose Verified....."); */
				
				// XPath for the Lead Disposition dropdown (adjust as per your actual element)
/*		        String dropdownXPath = "(//select)[2]";

		        // Expected dropdown options
		        List<String> expectedOptions = Arrays.asList(leadDisposition);

		        // Call the method to verify the dropdown options
		        verifyDropdownOptions(dropdownXPath, expectedOptions);  */
//				String expectedSelectedValue1=driver.findElements(By.xpath("(//select)[2]"))
				
				
/*				List<WebElement> LeadDis1 = driver.findElements(By.xpath("(//select)[2]"));

		        for (WebElement dis1 : LeadDis1) {
		            // Find the parent element of the checkbox
		            WebElement parent = dis1.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("dropdown value: " + dis1.getAttribute("value") + ", Text: " + leadDisposition);

		            // Click the checkbox if the text matches
		            if (leadDisposition.equals(dis1)) {
//		            Assert.assertEquals(leadDisposition, "dis1");
//		                if (!dis1.isSelected()) {
//		                    checkbox.click();
//		                	validateSelectedDropdownValue(By.xpath("(//select)[2]"),dis1);
		                	System.out.println("Verify Lead Disposition Primary....");
//		                }
//		                return; // Exit the method once the desired checkbox is clicked
		            	break;
		            }
		            else {
		            	System.out.println("Primary Lead Not Verified");
		            }
		        }  */
				
/*				validateSelectedDropdownValue(By.xpath("(//select)[2]"),"");
				Assert.assertEquals(leadDisposition, "Closed -No Sale");
				
				 System.out.println("Verify Lead Disposition1....");  */
//			}
			
			@Then("user Verify Lead Disposition Table Data Primary")
			public void user_verify_lead_disposition_table_data_primary() {
								
//				boolean isVisible = isTextVisibleInDropdown("(//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl  css-fvipm8'])[1]", leadDisposition1);
//		        Assert.assertTrue("Text not visible in dropdown", isVisible);
				
				String primary=driver.findElement(By.xpath("(//div[@role='combobox'])[1]")).getText();
				Assert.assertEquals(leadDisposition1, primary);
				 System.out.println("Verify Lead Disposition1....");
				 log.info("Verified primary Lead Disposition Table Data...");
			}
			
			@Then("user Verify Lead Disposition Secondary")
			public void user_verify_lead_disposition_secondary() {
//				boolean isVisible = isTextVisibleInDropdown("(//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl  css-fvipm8'])[2]", scndLeadDispos);
//		        Assert.assertTrue("Text not visible in dropdown", isVisible);
				
				String secondary=driver.findElement(By.xpath("(//div[@role='combobox'])[2]")).getText();
				Assert.assertEquals(scndLeadDispos, secondary);
				 System.out.println("Verify Lead Disposition2....");
				 log.info("Verified secondary Lead Disposition Table Data...");
			}
			
			/////////////////////////////////////////////////////
			///Lead Disposition On Table show on Map
			@When("user enter Audience Id in search Box {string}")
			public void user_enter_audience_id_in_search_box(String audienceId) {
//				this.audienceId=audienceId;
				///change window 
				Set<String> allPages=driver.getWindowHandles();
				for(String page: allPages)
				{
					driver.switchTo().window(page);
				}
				chngDisposMapToTabPg.entersAudienceIdInSearchbox(audienceId);
				tabAudId=audienceId;
				log.info("Entered Audience id in search box..."+tabAudId);
				staticWait(5000);
			}
			
			@Then("user click on Lead Coordinate {string}")
			public void user_click_on_lead_coordinate(String number) {
				staticWait(5000);
				driver.findElement(By.xpath("(//img[@role='button'])["+number+"]")).click();
				
//				Assert.assertEquals(tabAudId, null)
				log.info("Clicked on Lead Coordinate...");
				staticWait(5000);
			} 
			
			@When("user select primary lead disposition as {string}")
			public void user_select_primary_lead_disposition_as(String primaryDrop) {
				this.primaryDrop=primaryDrop;
				leadDisposTabToMapPg.clickonPrimaryLeadDropdown();
				selectOptionByText(By.xpath("//li[@role='option']"),primaryDrop);
				System.out.println("peimary select...");
				log.info("Primary Dropdown Selected....");
				staticWait(2000);
			}
			
			@When("user select secondary lead disposition as {string}")
			public void user_select_secondary_lead_disposition_as(String secondaryDrop) {
				this.secondaryDrop=secondaryDrop;
				leadDisposTabToMapPg.clickOnSecondaryLeadDropdown();
				selectOptionByText(By.xpath("//li[@role='option']"),secondaryDrop);
				log.info("Secondary Dropdown Selected....");
				staticWait(2000);
			}
			
			@When("click on Map button")
			public void click_on_map_button() {
				leadDisposTabToMapPg.clickOnMapButton();
				log.info("Clicked on Map Button....");
				staticWait(5000);
			}
			
			@Then("User Verify the Primary Lead Disposition dropdown on Map")
			public void user_verify_the_primary_lead_disposition_dropdown_on_map() {
				boolean isVisible = isTextVisibleInDropdown("(//select)[1]", primaryDrop);
		        Assert.assertTrue("Text not visible in dropdown", isVisible);
		        log.info("Validate Primary Dropdown....");
			}
			
			@Then("User Verify the Secondary Lead Disposition dropdown on Map")
			public void user_verify_the_Secondary_lead_disposition_dropdown_on_map() {
				boolean isVisible = isTextVisibleInDropdown("(//select)[2]", secondaryDrop);
		        Assert.assertTrue("Text not visible in dropdown", isVisible);
		        log.info("Validate Secondary Dropdown....");
			}
			
			
			/////////////////////////Get Zip Codes List ///////////
			@Then("Read and Verify first Zip Code Name")
			public void read_and_verify_first_zip_code_name() {
				 // Expected list of checkbox labels
/*	            List<String> expectedList = Arrays.asList("01010");

	            List<WebElement> actualList = driver.findElements(By.xpath("//ul[@class='nav nav-pills nav-sidebar flex-column']/child::span"));
				
	         // Flag to track if both checkboxes are found
		        for (WebElement drop : actualList) {
		        	 System.out.println("actual List:"+drop);
		        	
		            // Find the parent element of the checkbox
		            WebElement parent = drop.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("checkbox value: " + drop.getText() + ", Text: " + text);

		            // Print the name of each checkbox for debugging
//		            System.out.println("Checkbox name: " + actualList);
		            Assert.assertSame(text, expectedList);
		            
		            
	            } */

	            // Compare the actual list with the expected list
	            
	           

	            System.out.println("Test passed: Checkbox list matches expected list.");  
				// Example list of expected checkbox names
                List<String> expectedNames = List.of("01010");

	            // Validate checkboxes by their name and break after finding the match
	            validateCheckboxByName(expectedNames);
				
			}
			
			@Then("Read and Verify second Zip Code Name")
			public void read_and_verify_second_zip_code_name() {
				// Example list of expected checkbox names
	            List<String> expectedNames = List.of("01068");

	            // Validate checkboxes by their name and break after finding the match
	            validateCheckboxByName(expectedNames);
	            
	         
				
			}
			
			


/*			@Then("Read and Verify first Zip Code Name")
			public void read_and_verify_first_zip_code_name() {//ul[@role='menu']
				List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@value,'01')]"));////input[@type='checkbox']
				int totalElement=checkboxes.size();
				System.out.println("Total CheckBox Element:"+totalElement);

		        for (WebElement checkbox : checkboxes) {
		            // Find the parent element of the checkbox
		            WebElement parent = checkbox.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("Checkbox value: " + checkbox.getAttribute("value") + ", Text: " + text); 
				
		         // Validate checkbox1 visibility
		            if (checkbox.getText().equals("01010")) {
		                System.out.println("Checkbox 1 is visible.");
		                
		            }// else {
		                System.out.println("Checkbox 1 is not visible.");
		           // }
		            

		        } */
/*				List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

		        for (WebElement checkbox : checkboxes) {
		            // Find the parent element of the checkbox
		            WebElement parent = checkbox.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("Checkbox value: " + checkbox.getAttribute("value") + ", Text: " + text);

		            // Click the checkbox if the text matches
		            if (text.equals("01010")) {
	//	                if (!checkbox.isSelected()) {
	//	                    checkbox.click();
		            	System.out.println("CheckBox 1 Visible...");
		             //   }
		             //   return; // Exit the method once the desired checkbox is clicked
		            }
		         
		        }
		     // Throw an exception if no matching checkbox is found
		        throw new RuntimeException("Checkbox with text '" + "01010" + "' not found.");
//				staticWait(2000);
		        
			}  */
				
			
/*			@Then("Read and Verify second Zip Code Name")
			public void read_and_verify_second_zip_code_name() {
				List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@value,'01')]"));////input[@type='checkbox']
				int totalElement=checkboxes.size();
				System.out.println("Total CheckBox Element:"+totalElement);

		        for (WebElement checkbox : checkboxes) {
		            // Find the parent element of the checkbox
		            WebElement parent = checkbox.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("Checkbox value: " + checkbox.getAttribute("value") + ", Text: " + text); 
				
		         // Validate checkbox1 visibility
		            if (checkbox.getText().equals("01068")) {
		                System.out.println("Checkbox 2 is visible.");
		                
		            } //else {
		                System.out.println("Checkbox 2 is not visible.");
		         //   }
		            

		        }
				staticWait(2000);
			}  */
			
			/////////////////Select ZipCode Validate Boundary and Lead ////////////////
			@When("user Zoom out on Map")
			public void user_zoom_out_on_map() {
         ///move on map Window/////////
				
//				dynamicWebDriverWait(By.xpath("//div[text()='Assign']"),20);
//				System.out.println("wait for list button....");
				Set<String> allWindowHandles = driver.getWindowHandles();

				// Iterate through the handles and switch to the last one
				for (String windowHandle : allWindowHandles) {
				    driver.switchTo().window(windowHandle);
				}
				System.out.println("switch on Map Window");
				////mouse over action
				WebElement element=driver.findElement(By.xpath("//div[text()='Assign']"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
				dynamicWebDriverWait(By.xpath("//div[@class='leaflet-draw-toolbar leaflet-bar leaflet-draw-toolbar-top']"),30);
				
				/////click On Map Coordinate
/*				WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = 45; // base X coordinate
	            int baseY = 80; // base Y coordinate
//	            int offset = 140;  // offset to create multiple points

	            // Click to start drawing the polygon
	            act.moveToElement(mapArea, baseX, baseY).click().perform();
	            System.out.println("Clicked on map Window....");
	            staticWait(2000);
					zoomOutMapUsingKeyboard(2);
					System.out.println("Zoom Out Complete...."); */
				log.info("User Zoomed Out on Map...");
					staticWait(5000);		
			}
			
			@Then("Validate the Zip Code Boundary and Lead")
			public void validate_the_zip_code_boundary_and_lead() {
				// Wait for the map to load and the zip code boundary to appear
				// Assuming 'marker' is already added to the map and its coordinates are available
/*				String script = "return marker.getLatLng();";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				Map<String, Object> result = (Map<String, Object>) js.executeScript(script);
				Double lat = (Double) result.get("lat");
				Double lng = (Double) result.get("lng");

				System.out.println("Marker latitude: " + lat + ", longitude: " + lng);
				
				String polygonScript = "var latlng = marker.getLatLng(); " +
                        "var polygon = L.polygon([ " +
 //                       "[42.117743, -72.239746], [42.121241, -72.204330], [42.127234, -72.190367], [42.11371, -72.19152] " +
 "[42.132103, -72.255966]"+
                        "]); " +
                        "return polygon.contains(latlng);";
Boolean isInside = (Boolean) js.executeScript(polygonScript);

if (isInside) {
    System.out.println("Marker is inside the boundary.");
} else {
    System.out.println("Marker is outside the boundary.");
}

Assert.assertTrue("Marker should be inside the boundary", isInside);  */
				
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
	            WebElement zipCodeBoundary = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//img[@class='leaflet-marker-icon leaflet-zoom-animated leaflet-interactive']")));  // Adjust based on the actual element showing the zip code
	          
	            // Verify if the boundary of zip code 01010 is displayed on the map
	            if (zipCodeBoundary.isDisplayed()) {
	                System.out.println("Zip code boundary is visible on the map.");
	            } else {
	                System.out.println("Zip code boundary is not visible on the map.");
	            }  
	           log.info("Zip code Boundary Validated...");
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////
			/////////////// Agent Street Sorting/////////////////////////
			@Then("click on street Name for sorting order")
			public void click_on_street_name_for_sorting_order() {
				agentStreetSortingPg.clickOnStreetNameForSorting();
				log.info("Clicked On Street Name Header for Sorting...");
				staticWait(2000);
			}
			
			@Then("click on dropdown arrow")
			public void click_on_dropdown_arrow() {
				agentStreetSortingPg.clickOnArrowDropdownButton();
				log.info("Clicked On arrow dropdown...");
				staticWait(2000);
			}
			
			@Then("Store Table Data After Sorting")
			public void store_table_data_after_sorting() {
				// After sorting: Fetch data again from the 'Street Name' column
/*		        List<WebElement> rowsAfterSorting = driver.findElements(By.xpath("//table[@class='MuiTable-root css-1owb465']/tbody/tr/td[5]"));
		        List<String> streetNamesAfterSorting = new ArrayList<>();
		        for (WebElement row : rowsAfterSorting) {
		            streetNamesAfterSorting.add(row.getText());
		            System.out.println("After Sorting Data List:"+streetNamesAfterSorting);
		        }  */
		        
				alocNumberAfterSorting=agentStreetSortingPg.getALOCNumber();
				System.out.println("ALOC Number After Sorting: "+alocNumberAfterSorting);
				log.info("Stored Table Data after Sorting...");
			}
			
			@When("Store Table Data After Re-Login")
			public void store_table_data_after_re_login() {
				 // Before sorting: Fetch data from the 'Street Name' column
/*		        List<WebElement> rowsBeforeSorting = driver.findElements(By.xpath("//table[@class='MuiTable-root css-1owb465']/tbody/tr/td[5]")); // Assuming 4th column is 'Street Name'
		        List<String> streetNamesBeforeSorting = new ArrayList<>();
		        for (WebElement row : rowsBeforeSorting) {
		            streetNamesBeforeSorting.add(row.getText());
		            System.out.println("After Re-Login Data List:"+streetNamesBeforeSorting);
		        } */
				alocNumberAfterReLogin=agentStreetSortingPg.getALOCNumber();
				System.out.println("ALOC Number After Re-Login: "+alocNumberAfterReLogin);
				log.info("Stored Table Data after Re-Login...");
		     
			}
			
			@Then("Validate the Sorted Table data after Re-Login")
			public void validate_the_sorted_table_data_after_re_login() {
				// Validate that the data matches
/*		        if (streetNamesAfterSorting.equals(streetNamesAfterRelogin)) {
		            System.out.println("Validation Passed: Table data is consistent after re-login.");
		        } else {
		            System.out.println("Validation Failed: Table data is not consistent after re-login.");
		        }
		        // Additional debugging to identify discrepancies
		        for (int i = 0; i < Math.min(streetNamesAfterSorting.size(), streetNamesAfterRelogin.size()); i++) {
		            if (!streetNamesAfterSorting.get(i).equals(streetNamesAfterRelogin.get(i))) {
		                System.out.println("Mismatch found at index " + i + ":");
		                System.out.println("Before Re-Login: " + streetNamesAfterSorting.get(i));
		                System.out.println("After Re-Login: " + streetNamesAfterRelogin.get(i));
		            }
		        }  */
				
				
/*				 if (alocNumberAfterSorting.equals(alocNumberAfterReLogin)) {
			            System.out.println("Test Passed: ALOC Numbers match.");
			        } else {
			            System.out.println("Test Failed: ALOC Numbers do not match.");
			            System.out.println("ALOC Number After Sorting: " + alocNumberAfterSorting);
			            System.out.println("ALOC Number After Re-Login: " + alocNumberAfterReLogin);
			        }  */
				System.out.println("ALOC after Sort:"+alocNumberAfterSorting);
				System.out.println("ALOC after Re-Login:"+alocNumberAfterReLogin);
				Assert.assertEquals(alocNumberAfterSorting, alocNumberAfterReLogin);
				log.info("Validated Table Data ...");
				
		    }
			
			///////////////////////////////////////////////////////////////////////////
			/////// Agent Re-arrange Table Data Drag and Drop ///////////////////
			@Then("click on Sort Icon")
			public void click_on_sort_icon() {
				dragAndDropAgentDataPg.clickOnSortIcon();
				log.info("Clicked on Sort Icon ...");
				staticWait(3000);
			}
			
			@Then("Drag a Row of Table and Drop")
			public void drag_a_row_of_table_and_drop() {
				WebElement source=driver.findElement(By.xpath("(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-fontSizeLarge css-6flbmm'])[2]"));
				WebElement target=driver.findElement(By.xpath("(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-fontSizeLarge css-6flbmm'])[4]"));
				Actions act=new Actions(driver);
//				act.dragAndDrop(source, target);
				act.clickAndHold(source) // Click and hold the source row
                .moveToElement(target) // Move to the target row
                .release() // Release the mouse button
                .build() // Build the action chain
                .perform(); // Perform the action
				log.info("Dragged and Dropped table Row ...");
				staticWait(8000);
			}

				//////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////////////////////////////
				/// Agent Lead Filter ///////////////////////////
			@When("user Zoom In on Dealer Map")
			public void user_zoom_in_on_dealer_map() {
				dynamicWebDriverWait(By.xpath("//div[text()='Assign']"),20);
				System.out.println("wait for list button....");
				Set<String> allWindowHandles = driver.getWindowHandles();

				// Iterate through the handles and switch to the last one
				for (String windowHandle : allWindowHandles) {
				    driver.switchTo().window(windowHandle);
				}
				System.out.println("switch on Map Window");
				
				////mouse over action
				WebElement element=driver.findElement(By.xpath("//div[text()='Assign']"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
				staticWait(10000);
				/////click On Map Coordinate
				WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = 45; // base X coordinate
	            int baseY = 75; // base Y coordinate
//	            int offset = 140;  // offset to create multiple points

	            // Click to start drawing the polygon
	            act.moveToElement(mapArea, baseX, baseY).click().perform();
	            System.out.println("Clicked on map Window....");
	            staticWait(2000);
	//				zoomInMapUsingKeyboard(1);
					System.out.println("Zoom in Complete....");
					log.info("Map Zoom Success...");
					staticWait(2000);
				
			}  
			
			@Then("user draw a Polygon on Dealer Map")
			public void user_draw_a_polygon_on_dealer_map() {
				// Zoom in on Dealer Map
//				zoomInMapUsingKeyboard(1);
				// Initialize Actions class object to perform mouse actions
	            Actions builder = new Actions(driver);

	            // Locate map area, assuming it's uniquely identified for actions
	            WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = 400; // base X coordinate   X-axis (width): 0 to 1919
	            int baseY = 100; // base Y coordinate   Y-axis (height): 0 to 1079
	            int offset = 250;  // offset to create multiple points

	            // Click to start drawing the polygon
	            builder.moveToElement(mapArea, baseX, baseY).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX + offset, baseY).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX + offset, baseY + offset).click().perform();
	            staticWait(3000);
	            builder.moveToElement(mapArea, baseX, baseY + offset).click().perform();
	            staticWait(3000);

	            // Complete the polygon by clicking on the first point again
	            builder.moveToElement(mapArea, baseX, baseY).click().perform();
	            log.info("Created Polygon....");
	            staticWait(3000);
	            System.out.println("draw polygone....");
	            staticWait(5000);
			}  
			    
			
			@Then("click on Filter Icon")
			public void click_on_filter_icon() {
				staticWait(5000);
				dynamicWebDriverWait(By.xpath("//img[@class='leaflet-marker-icon leaflet-zoom-animated leaflet-interactive']"),30);
			    agentLeadFilterPg.clickOnFilterIcon();
			    log.info("Clicked on filter Icon...");
			    staticWait(2000);
			} 
			
			@Then("select Lead Type dropdown as {string}")
			public void select_lead_type_dropdown_as(String leadType) {
				agentLeadFilterPg.clickOnLeadTypeDropdown();
				log.info("Clicked on lead type dropdown...");
				selectOptionByText(By.xpath("//li[@role='option']"),leadType);
				log.info("Selected lead type dropdown...");
				staticWait(2000);
			}
			
			@Then("select Disposition dropdown as {string}")
			public void select_disposition_dropdown_as(String disposition) {
				agentLeadFilterPg.clickOnDispositionDropdown();
				log.info("Clicked on Disposition dropdown...");
				selectOptionByText(By.xpath("//li[@role='option']"),disposition);
				log.info("Selected disposition dropdown...");
				staticWait(2000);
			}
			
			@Then("click on Apply button")
			public void click_on_apply_button() {
				agentLeadFilterPg.clickOnApplyButton();
				log.info("clicked on Apply button...");
				staticWait(5000);
			}
			
			@When("user Zoom In on Agent Map")
			public void user_zoom_in_on_agent_map() {
				dynamicWebDriverWait(By.xpath("//button[text()='List']"),20);
				System.out.println("wait for list button....");
				Set<String> allWindowHandles = driver.getWindowHandles();

				// Iterate through the handles and switch to the last one
				for (String windowHandle : allWindowHandles) {
				    driver.switchTo().window(windowHandle);
				}
				System.out.println("switch on Map Window");
				
				////mouse over action
				WebElement element=driver.findElement(By.xpath("//button[text()='List']"));
				Actions act=new Actions(driver);
				act.moveToElement(element).perform();
				staticWait(5000);
				/////click On Map Coordinate
				WebElement mapArea = driver.findElement(By.xpath("//div[@class='leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom']"));

	            // Coordinates to click on, these should be determined by trial and error or dynamically calculated
	            int baseX = 45; // base X coordinate
	            int baseY = 75; // base Y coordinate
//	            int offset = 140;  // offset to create multiple points

	            // Click to start drawing the polygon
	            act.moveToElement(mapArea, baseX, baseY).click().perform();
	            System.out.println("Clicked on map Window....");
	            staticWait(2000);
					zoomInMapUsingKeyboard(2);
	         

					System.out.println("Zoom in Complete....");
					log.info("Map Zoom Success...");
					staticWait(2000);
				
			}  
			
			@Then("count Number of Leads on Map")
			public void count_number_of_leads_on_map() {
/*				WebElement connector=driver.findElement(By.xpath("(//img[@src='https://bucketeer-463fb613-53c5-46a0-bb69-cd5d8fd3cfcf.s3.amazonaws.com/public/images/icons/nds_connector.png'])[1]"));
				Actions actions = new Actions(driver);
				actions.moveToElement(connector).click().perform(); */

//				staticWait(3000);
				// Locator for lead elements
				List<WebElement> initialLeads = driver.findElements(By.xpath("//img[contains(@src,'wireless') or contains(@src,'fiber')]"));
		        
		        // Check if the list is empty and print the appropriate message
	            if (initialLeads.isEmpty()) {
	                System.out.println("No leads found on the map.");
	            } else {
	                // Print out the number of leads found
	            	initialLeadCount=initialLeads.size();
	                System.out.println("Initial leads Find on map: " + initialLeadCount);
	                List<WebElement> connectors = driver.findElements(By.xpath("//img[contains(@src,'connector.png')]"));
	                
	                if(connectors.isEmpty()) {
	                	System.out.println("No connectors found on map.");
	                } else {
	                	int connectorCount =connectors.size();
	                	System.out.println("Total connectors on map:"+connectorCount);
	                	
	                	//loop for connector click 
	                	for(int i=0;i<=connectorCount;i++)
	                	{
	                	
	                            // Click on the connector
	                            WebElement connector = connectors.get(i);
	                            System.out.println("iterate loop..."+connector);
	                            connector.click();
	                            System.out.println("Clicking on connector " + (i + 1) + ": " + connector);
	                            staticWait(2000);
	                         // Recount leads after clicking the connector
	                            List<WebElement> updatedLeads = driver.findElements(By.xpath("//img[contains(@src,'wireless') or contains(@src,'fiber')]"));
	                            int newLeadsCount = updatedLeads.size();
	                            tempLeadCount += (newLeadsCount - tempLeadCount); // Update tempLeadCount
	                            System.out.println("Updated leads found on map: " + newLeadsCount);
	                            connector.click();
	                            staticWait(2000);
	                         // Print the final total lead count after processing all connectors
	    	                    System.out.println("Final total lead count on map: " + tempLeadCount);
	                	}
	                	
	                }
	             
	         
	            } 
	            } 
			
			
/*			@Then("count Number of Leads on Map")
			public void count_number_of_leads_on_map() {
				List<WebElement> leads = driver.findElements(By.xpath("//img[@class='leaflet-marker-icon leaflet-zoom-animated leaflet-interactive']"));
		        
		        // Check if the list is empty and print the appropriate message
	            if (leads.isEmpty()) {
	                System.out.println("No leads found on the map.");
	            } else {
	                // Print out the number of leads found
	            	 leadCount=leads.size();
	                System.out.println("Total leads on map: " + leadCount);
	            }
			}  */
			
			@Then("Validate Number of Leads on List Table")
			public void validate_number_of_leads_on_list_table() {
				String leadsOnListTable=agentLeadFilterPg.getLeadsOnList();
				System.out.println("Lead List:"+leadsOnListTable);
				int lead=getNumberAfterOf(By.xpath("//p[@class='MuiTablePagination-displayedRows css-1chpzqh']"));
				System.out.println("Lead:"+lead);
				Assert.assertEquals(initialLeadCount, lead);
				System.out.println("Lead Match Map And Table...");
			}
			
			@Then("Convert the List Table In Card View")
			public void convert_the_list_table_in_card_view() throws AWTException {
				Robot rb=new Robot();
				rb.delay(2000);
				for(int i=0;i<4;i++)
				{
					rb.keyPress(KeyEvent.VK_CONTROL);
					rb.keyPress(KeyEvent.VK_ADD);
					
					rb.keyRelease(KeyEvent.VK_ADD);
					rb.keyRelease(KeyEvent.VK_CONTROL);
					log.info("entered Completeion Date....");
					rb.delay(2000);
				}
				staticWait(5000);
			}
			
			@Then("Validate Number of Leads for Card View")
			public void validate_number_of_leads_for_card_view() {
				String leadsOnCard=agentLeadFilterPg.getLeadsOnCard();
				System.out.println("Card Leads Number:"+leadsOnCard);
				int cardLead=getNumberAfterOf(By.xpath("//span[contains(text(), 'out of')]"));
			System.out.println("Card Lead:"+cardLead);
			Assert.assertEquals(initialLeadCount, cardLead);
			System.out.println("Leads Match Map Lead And Card View...");
			}
			
	
/*	@After
	public void teardown(Scenario sc)
	{
		System.out.println("tear down method executed...");
		
		if(sc.isFailed()==true)
		{
			//Convert web driver object to TakeScreenshot

			String fileWithPath = "D:\\ATT_GitRepo\\selenium-automation-testing\\Screenshot\\failedScreenshot.png";
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			//Move image file to new destination
			File DestFile=new File(fileWithPath);

			//Copy file at destination

			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		driver.quit();
	} */
			
			
			@After
			public void teardown(Scenario sc) {
			    System.out.println("tear down method executed...");
			    
			    if (sc.isFailed()) {
			        // Generate a unique file name using the scenario name and timestamp
			        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			        String scenarioName = sc.getName().replaceAll("[^a-zA-Z0-9]", "_"); // Sanitize name for file compatibility
			        String fileWithPath = "D:\\ATT_GitRepo\\selenium-automation-testing\\Screenshot\\" + scenarioName + "_" + timestamp + ".png";

			        // Take screenshot
			        TakesScreenshot scrShot = ((TakesScreenshot) driver);
			        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			        // Save screenshot to the destination path
			        File DestFile = new File(fileWithPath);
			        try {
			            FileUtils.copyFile(SrcFile, DestFile);
			            System.out.println("Screenshot saved at: " + fileWithPath);
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
			    
			    driver.quit();
			}


}
